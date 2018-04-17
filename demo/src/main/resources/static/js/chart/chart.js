$(document).ready(function (){
	
	Highcharts.chart('container', {
	    title: {
	        text: 'Proportion chart'
	    },
	    xAxis: {
	        categories: ['SRS', 'PDF', 'PNG']
	    },
	    labels: {
	        items: [{
	            html: 'Total Upload',
	            style: {
	                left: '50px',
	                top: '18px',
	                color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
	            }
	        }]
	    },
	    series: [{
	        type: 'column',
	        name: 'Upload Count',
	        data: [{
	          "name": "SRS",
	          "y": 3,
	          "drilldown": "SRS",
	          color: Highcharts.getOptions().colors[0]
	        },{
	          "name": "PDF",
	          "y": 2,
	          "drilldown": "PDF",
	          color: Highcharts.getOptions().colors[1]
	        },{
	          "name": "PNG",
	          "y": 6,
	          "drilldown": "PNG",
	          color: Highcharts.getOptions().colors[2]
	        }],showInLegend: false,
	        dataLabels: {
	            enabled: false
	        }
	    },{
	        type: 'spline',
	        name: 'Upload Count',
	        data: [{
	          "name": "SRS",
	          "y": 3,
	          "drilldown": "SRS",
	          color: Highcharts.getOptions().colors[0]
	        },{
	          "name": "PDF",
	          "y": 2,
	          "drilldown": "PDF",
	          color: Highcharts.getOptions().colors[1]
	        },{
	          "name": "PNG",
	          "y": 6,
	          "drilldown": "PNG",
	          color: Highcharts.getOptions().colors[2]
	        }],
	        marker: {
	            lineWidth: 2,
	            lineColor: Highcharts.getOptions().colors[3],
	            fillColor: 'white'
	        },showInLegend: false,
	        dataLabels: {
	            enabled: false
	        }
	    }, {
	        type: 'pie',
	        name: 'Upload Count',
	        data: [{
	            name: 'SRS',
	            y: 3,
	            color: Highcharts.getOptions().colors[0] // Jane's color
	        }, {
	            name: 'PDF',
	            y: 2,
	            color: Highcharts.getOptions().colors[1] // John's color
	        }, {
	            name: 'PNG',
	            y: 6,
	            color: Highcharts.getOptions().colors[2] // Joe's color
	        }],
	        center: [100, 80],
	        size: 100,
	        showInLegend: false,
	        dataLabels: {
	            enabled: false
	        }
	    }]
	});
	
});