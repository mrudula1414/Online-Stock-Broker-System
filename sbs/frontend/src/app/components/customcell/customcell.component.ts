import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GetDataService } from '../../app/services/get-data.service';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../services/authenticate.service';

@Component({
  selector: 'app-customcell',
  templateUrl: './customcell.component.html',
  styleUrls: ['./customcell.component.css']
})
export class CustomcellComponent implements OnInit {

  watchList;
  currentusername:String;
  data:any;
  params:any;
  constructor(private router: Router, 
    private getDataservice: GetDataService, 
    private toastr: ToastrService,
    private watchService:AuthenticationService) { }
  agInit(params)
  { 
    this.params=params;
    this.data=params.value;
  }

  ngOnInit() {
    this.getDataservice.getWatchList(localStorage.getItem('username'))
    .subscribe(
      data => {
        if(data.length)
          this.watchList = data;
      },
      error => {
          console.log(error)
      }
    )
  }
  addToWatchlist(companyName,companyId)
  {
  this.getDataservice.addWatchList(companyName,companyId)
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
  
  addingToWatchlist()
 {
  console.log(localStorage.getItem("username"))
  console.log(this.data)
  this.addToWatchlist(localStorage.getItem("username"), this.data);
 
 
 }
   /*console.log(localStorage.getItem("username"))
   console.log(this.data)

   this.watchService.addtoWatchlist(localStorage.getItem('username'),this.params)
    .subscribe(
      data => {console.log(data)
        if(data.status=='company exist')
          this.toastr.error('Already Exsist to your watchlist', this.params,{positionClass:"toast-bottom-center"});
        else
          this.toastr.info('Successfully added to your Watch list', this.params,{positionClass:"toast-bottom-center"});
      },
      error => {
          console.log(error)
      }*/
    //)
    /*this.watchService.addtoWatchlist(this.params,this.currentusername)
  
    .subscribe(
      data => {console.log(data)
        if(data.status=='company exist')
          this.toastr.error('Already Exists in your watchlist', this.params,{positionClass:"toast-bottom-center"});
        else{
          this.toastr.info('Successfully added to your Watch list',this.params,{positionClass:"toast-bottom-center"});
    
          console.log(this.params)*/
       // alert("successfully added");
      
   /* },
      error => {
          console.log(error)
      }
    )
        
  }*/

}

