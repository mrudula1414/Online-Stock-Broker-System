import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GetDataService } from '../../services/get-data.service';
import { HttpClient } from '@angular/common/http';
import { AuthenticationService } from '../../services/authenticate.service'
import { CellRendererComponent } from 'ag-grid-community/dist/lib/components/framework/componentTypes';
import { CustomcellComponent } from 'src/app/customcell/customcell.component';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

 companyDetails;
  constructor(private router: Router, 
              private getDataservice: GetDataService, 
              private toastr: ToastrService,
              private loginService:AuthenticationService,
              private http: HttpClient) { }
              columnDefs=[
                { headerName: "Stock Symbol", field: "name", sortable:true,filter:true,resizable:true,width:200,cellClass: "grid-cell-centered"},
                { headerName: "open rate", field: "open_rate", sortable:true,filter:true,resizable:true,width:200},
                { headerName: "close rate", field: "close_rate",sortable:true,filter:true,resizable:true,width:200},
                { headerName: "current rate", field: "current_rate", sortable:true,filter:true,resizable:true,width:200},
                { headerName: "volume", field: "volume", sortable:true,filter:true,resizable:true,width:200},
                //{ headerName: "currentrate", field: "current_rate", sortable:true,filter:true,resizable:true,width:200},
                { headerName: "Action", field: "company_id", "cellRendererFramework":CustomcellComponent,width:200},
               //{ headerName: "Action", field: "company_id",cellRendererFramework:(params)=><div>
                //<button>Watch</button></div>},
                //{ headerName: "Action", field: "company_id",width:200,onClick={()=>actionButton(params)},cellRendererParams:(params)=>},
                
                ];
                rowData:any;
                rowHeight: number;
                
              
               
  ngOnInit() {
    this.rowData=this.http.get("http://localhost:8080/get-all")
    this.rowHeight = 40;
    this.getDataservice.getAllCompany()
    
    .subscribe(
      data => {
          this.companyDetails = data;
      },
      error => {
          console.log(error)
      }
    )
  }

  addToWatchlist(companyId, companyName)
  {
    this.getDataservice.addWatchList(localStorage.getItem('username'),companyId)
    .subscribe(
      data => {console.log(data)
        if(data.status=='company exist')
          this.toastr.error('Already Exsist to your watchlist', companyName,{positionClass:"toast-bottom-center"});
        else
          this.toastr.info('Successfully added to your Watch list', companyName,{positionClass:"toast-bottom-center"});
      },
      error => {
          console.log(error)
      }
    )
  }

  goToBuyPage(companyId)
  {    
    this.router.navigate(['buy',companyId])
  }
  goToDetailedView(companyId)
  {    
    this.router.navigate(['view',companyId])
  }

 /* title = 'aggrid';

  columnDefs=[
  { headerName: "Company name", field: "name", sortable:true,filter:true,resizable:true,innerWidth:30},
  { headerName: "open rate", field: "open_rate", sortable:true,filter:true,resizable:true,innerWidth:30},
  { headerName: "close rate", field: "close_rate",sortable:true,filter:true,resizable:true,innerWidth:30},
  { headerName: "current rate", field: "current_rate", sortable:true,filter:true,resizable:true,innerWidth:30},
  { headerName: "volume", field: "volume", sortable:true,filter:true,resizable:true,innerWidth:30},
  { headerName: "currentrate", field: "current_rate", sortable:true,filter:true,resizable:true,innerWidth:30},
  //{ headerName: "Action", field: "volume", "cellRendererFramework":viewComponent,sortable:true,filter:true,resizable:true,innerWidth:30},
  { headerName: "Action", field: "company_id","cellRendererFramework":CustomcellComponent,innerWidth:30},
 
 
  
  ];
  rowData:any;
  rowHeight: number;
  constructor(private http: HttpClient)
  {
   
  }
  ngOnInit()
  {ng
    this.rowData=this.http.get("http://localhost:8080/get-all")
    this.rowHeight = 40;
    
}*/
}
