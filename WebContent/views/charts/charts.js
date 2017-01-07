DBSCANApp.controller('chartsController', function($scope,localStorageService) {

    /**
     * Generate Point Series to plot
     */
    function getSeries(pointsList){
       
        var mainData = [];
        // go through clusters
        for(var c=0;c<pointsList.length;c++){
            var dataTemp = [];
            for(var i=0;i<pointsList[c].points.length;i++){
                dataTemp.push([pointsList[c].points[i].x,pointsList[c].points[i].y]);
            }
            mainData.push({
                name: 'Cluster '+pointsList[c].cluster,  
                color: 'rgb('+getRandomNumber()+', '+getRandomNumber()+', '+getRandomNumber()+')',
                data:dataTemp
            });
        }
        
        return mainData;
    };
    
    /**
     * get random number
     */
    function getRandomNumber(){
        return Math.floor(Math.random() * ((255-0)+1) + 0);
    };
    
    $scope.loadchart = function (){
        $('#chart').highcharts({
            chart: {
                type: 'scatter',
                zoomType: 'xy'
            },
            title: {
                enabled: false,
                text: ''
            },
            xAxis: {
                title: {
                    enabled: false,
                    text: 'X'
                },
                startOnTick: true,
                endOnTick: true,
                showLastLabel: true
            },
            yAxis: {
                title: {
                    enabled: false,
                    text: 'Y'
                }
            },
            plotOptions: {
                scatter: {
                    marker: {
                        radius: 5,
                        states: {
                            hover: {
                                enabled: true,
                                lineColor: 'rgb(100,100,100)'
                            }
                        },
                        symbol:"circle"
                    },
                    states: {
                        hover: {
                            marker: {
                                enabled: false
                            }
                        }
                    },
                    tooltip: {
                        headerFormat: '<b>{series.name}</b><br>',
                        pointFormat: 'X - {point.x},Y - {point.y}'
                    }
                }
            },
            series: getSeries(localStorageService.get('chartData').pointsList)
        });
    };
    
    $scope.loadchart();
    
});