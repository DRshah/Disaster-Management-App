$( document ).ready(function() {
    console.log( "ready!" );

    console.log(localStorage.getItem("orgName"));

    $('#orgHeaderName').html(localStorage.getItem("orgName").toUpperCase());

    $('#orgDisasterName').html(localStorage.getItem("disasterName").toUpperCase());

    var camelcase = localStorage.getItem("orgName").replace(/\s(.)/g, function($1) { return $1.toUpperCase(); })
        .replace(/\s/g, '')
        .replace(/^(.)/, function($1) { return $1.toLowerCase(); });

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

    var dbRef = firebase.database().ref().child('orgs').child(camelcase||'goonj');
    //To load from database once
    dbRef.once('value').then(function(sn){
        //console.log(sn.val());

        var resources = sn.val()['resources'];

        var reqd = 0;
        var supplied = 0;
        var transit = 0;

        var chartObj = {};


        for(var key in resources){

            for(var item in resources[key]){

                //console.log(item);
                if(!chartObj[item]){
                    chartObj[item] = {};
                }

                for(var mode in resources[key][item]){

                    //console.log(mode, resources[key][item]);
                    //console.log(mode);
                    if(!chartObj[item][mode]){chartObj[item][mode] = 0;}

                    chartObj[item][mode] = chartObj[item][mode] + resources[key][item][mode];

                    if(mode == 'req') {
                        reqd = reqd + resources[key][item][mode];

                    }

                    if(mode == 'trans') {
                        transit = transit + resources[key][item][mode];
                    }

                    if(mode == 'supplied') {
                        supplied = supplied + resources[key][item][mode];
                    }
                    //console.log(reqd, transit, supplied);

                }

            }

        }

        console.log(chartObj);

        //console.log(reqd, transit, supplied);

        $('#reqd').html(reqd);
        $('#transit').html(transit);
        $('#supplied').html(supplied);




        var categoriesLabel = ['Food',  'Medicines'];
        var stackedBarData = [{name:'Distributed', data:[chartObj.food.supplied, chartObj.medicine.supplied]},
            {name:'Transit', data:[chartObj.food.trans,chartObj.medicine.trans]} ,{name: 'Required', data:[chartObj.food.req, chartObj.medicine.req]}];
        $(function () {
            var myChart = Highcharts.chart('2ColBarChart', {
                chart: {
                    type: 'bar'
                },
                title: {
                    text: 'Resources by Categories'
                },
                xAxis: {
                    categories: categoriesLabel
                },
                yAxis: {
                    title: {
                        text: '#items'
                    }
                },
                series: stackedBarData
            });
        });


    });


});