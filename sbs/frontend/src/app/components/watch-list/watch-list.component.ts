import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GetDataService } from '../../services/get-data.service';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/services/authenticate.service';

@Component({
  selector: 'app-watch-list',
  templateUrl: './watch-list.component.html',
  styleUrls: ['./watch-list.component.css']
})
export class WatchListComponent implements OnInit {

  constructor(private router: Router,private toastr: ToastrService, private getDataservice: GetDataService,private watchService:AuthenticationService) { }
  watchList;
  ngOnInit() {
    this.loadData();
  }  
  loadData()
  {
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
  
  removeFromWatchlist(companyId, companyName)
  {
    this.getDataservice.removeWatchList(localStorage.getItem('username'),companyId)
      .subscribe(
        data => {
            if(data.status=="success")
            {
              this.loadData();
              this.toastr.success('Successfully removed from your Watchlist', companyName,{positionClass:"toast-bottom-center"});
            }
              
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

}
