$( document ).ready(function() {


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

            var locationMap = {};

            for(var key in resources){

                console.log(key);
                if(!locationMap[key]) locationMap[key.toString()] = {};

                for(var item in resources[key]){

                    //console.log(item);
                    if(!chartObj[item]){
                        chartObj[item] = {};
                    }

                    for(var mode in resources[key][item]){

                        console.log(mode, resources[key][item]);
                        //console.log(mode);
                        if(!chartObj[item][mode]){chartObj[item][mode] = 0;}

                        if(!locationMap[key][mode]){locationMap[key][mode] = 0;}

                        chartObj[item][mode] = chartObj[item][mode] + resources[key][item][mode];

                        if(mode == 'req') {
                            reqd = reqd + resources[key][item][mode];
                            locationMap[key][mode] = locationMap[key][mode] + reqd;
                        }

                        if(mode == 'trans') {
                            transit = transit + resources[key][item][mode];
                        }

                        if(mode == 'supplied') {
                            supplied = supplied + resources[key][item][mode];
                            locationMap[key][mode] = locationMap[key][mode] + supplied;
                        }
                        //console.log(reqd, transit, supplied);

                    }

                }

            }

            //var child = {};
            //child[obj] = [supplied, transit, reqd];
            dataArr[obj] = [supplied, transit, reqd];
        }

        //console.log(dataArr);
        console.log(locationMap);

    });


});