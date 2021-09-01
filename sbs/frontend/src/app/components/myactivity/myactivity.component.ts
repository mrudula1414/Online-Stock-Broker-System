import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BuybuttonComponent } from '../buybutton/buybutton.component';
import { DetailsbuttonComponent } from '../detailsbutton/detailsbutton.component';
import { SellbuttonComponent } from '../sellbutton/sellbutton.component';
import { AuthenticationService } from '../services/authenticate.service';
import { GetDataService } from '../services/get-data.service';

@Component({
  selector: 'app-myactivity',
  templateUrl: './myactivity.component.html',
  styleUrls: ['./myactivity.component.css']
})
export class MyactivityComponent implements OnInit {

   
  constructor(private router: Router, 
    private getDataservice: GetDataService, 
    private loginService:AuthenticationService,
    private http: HttpClient) { }
    columnDefs=[
      { headerName: "Time Stamp", field: "time", sortable:true,filter:true,resizable:true,width:180,cellClass:"grid-cell-centered",sort:"desc"},
      { headerName: "Item", field: "item", sortable:true,filter:true,resizable:true,width:150,cellClass:"grid-cell-centered"},
      { headerName: "Action", field: "action", sortable:true,filter:true,resizable:true,width:150,cellClass:"grid-cell-centered"},
      { headerName: "Remarks", field: "remarks", sortable:true,filter:true,resizable:true,width:250,cellClass:"grid-cell-centered"}
      ];
      rowData:any;
      rowHeight: number;
      
    
     
ngOnInit() {
  const mailid=localStorage.getItem("username");
this.rowData=this.http.get("http://localhost:8080/myactivity?email="+mailid)
this.rowHeight = 40;

}


}
