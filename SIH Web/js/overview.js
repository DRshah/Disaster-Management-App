$( document ).ready(function() {

    data = {
        datasets: [{
            data: [9, 7, 35],
            backgroundColor: ['rgba(0, 255, 120, 0.7)', 'rgba(255, 255, 0, 0.7)', 'rgba(255, 50, 0, 0.7)']

        }],

        // These labels appear in the legend and in the tooltips when hovering different arcs
        labels: [
            'Distributed',
            'in-transit',
            'Required'
        ]
    };

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

    var dbRef = firebase.database().ref().child('orgs');

    var dataArr = {};

    dbRef.once('value').then(function(sn){
        //console.log(sn.val());

        for(var obj in sn.val()){
            //console.log(obj);

            var resources = sn.val()[obj]['resources'];

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

            //var child = {};
            //child[obj] = [supplied, transit, reqd];
            dataArr[obj] = [supplied, transit, reqd];
        }

        console.log(dataArr);

        var donutIds = [];
        $('.donut').each(function () {
            //console.log(this.id);
            donutIds.push(this.id);
        });

        //console.log(donutDivs);

        var donutNames = [];

        var cnt = 0;
        for(var key in $('.mr-5')){

            cnt++;
            if(cnt == 4) break;

            //console.log(key);
            donutNames.push($('.mr-5')[key]['innerText'].replace(/\s(.)/g, function($1) { return $1.toUpperCase(); })
                .replace(/\s/g, '')
                .replace(/^(.)/, function($1) { return $1.toLowerCase(); }));

        }
        //console.log(donutNames);

        for(var donut in donutIds){

            //console.log(donut, donutNames[donut], dataArr[donutNames[donut]]);

            var stringID = donutIds[donut];
            //console.log(stringID);
            if(stringID == undefined) continue;

            var ctx = document.getElementById(stringID).getContext('2d');
            var myDoughnutChart = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    datasets: [{
                        data: dataArr[donutNames[donut]],
                        backgroundColor: ['rgba(0, 255, 120, 0.7)', 'rgba(255, 255, 0, 0.7)', 'rgba(255, 50, 0, 0.7)']

                    }],

                    // These labels appear in the legend and in the tooltips when hovering different arcs
                    labels: [
                        'Distributed',
                        'in-transit',
                        'Required'
                    ]
                },
                options: {}
            });

        }




    });




});