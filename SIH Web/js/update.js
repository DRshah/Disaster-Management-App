var region = '9';

$( document ).ready(function() {

     region = '9';

    $(".dropdown-menu li a").click(function(){

        $(".btn:first-child").text($(this).text());
        $(".btn:first-child").val($(this).text());

        console.log($(".btn:first-child").text($(this).text()));
        region =  $(".btn:first-child").val($(this).text())[2]['innerHTML'].split(' ')[1];
        console.log(region);
        $('#but').html("Update deliveries!");

        $('#but2').html("Update Requests!");

    });


});

function updateDB() {

    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyAtcUwhzA6pUe9JnKS0qExtn7mefsdzP28",
        authDomain: "tcdisrupt17.firebaseapp.com",
        databaseURL: "https://tcdisrupt17.firebaseio.com",
        projectId: "tcdisrupt17",
        storageBucket: "tcdisrupt17.appspot.com",
        messagingSenderId: "921359473730"
    };
    firebase.initializeApp(config);


    var camelcase = localStorage.getItem("orgName").replace(/\s(.)/g, function ($1) {
        return $1.toUpperCase();
    })
        .replace(/\s/g, '')
        .replace(/^(.)/, function ($1) {
            return $1.toLowerCase();
        });

    console.log("org", camelcase);

    var dbRef = firebase.database().ref().child('orgs').child(camelcase || "goonj");

    dbRef.once('value').then(function (sn) {

        console.log(sn.val());

        var foodCnt = $('#foodCount').val() || 0;
        var medicinesCnt = $('#medicinesCount').val() || 0;
       // var clothesCnt = $('#clothesCount').val() || 0;

        //console.log(foodCnt, medicinesCnt, clothesCnt);

        var newFoodCnt = sn.val()['resources'][region]['food']['req'] - foodCnt;
        var newmedCnt = sn.val()['resources'][region]['medicine']['req'] - medicinesCnt;

        var retObj = {'food':{'req':newFoodCnt, 'trans':sn.val()['resources'][region]['food']['trans'], 'supplied': sn.val()['resources'][region]['food']['supplied']},
            'medicine':{ 'req':newmedCnt, 'trans':sn.val()['resources'][region]['medicine']['trans'], 'supplied': sn.val()['resources'][region]['medicine']['supplied']}};

        console.log(retObj);

        var orgsRef = dbRef.child('resources').child(region);

        orgsRef.set(retObj);

        alert("The values have been updated!");

    });



}