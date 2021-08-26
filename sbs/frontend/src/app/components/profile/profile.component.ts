import { Component, OnInit } from '@angular/core';
import { GetDataService } from '../../services/get-data.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  

  constructor(private getDataservice: GetDataService,private router: Router) { }
  userDetails;
  ngOnInit() {
    this.getDataservice.getUserDeatils(localStorage.getItem('username'))
    .subscribe(
      data => {
          this.userDetails = data;
          console.log(data);
      },
      error => { }
    )
  }
  goToAddFunds(){
    this.router.navigate(['addfunds'])
  }

}
