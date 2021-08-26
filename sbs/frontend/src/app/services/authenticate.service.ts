import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface UserResponse {
  [x: string]: any;  
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService 
{
    baseUrl = "http://localhost:8080/";
    url="";
    constructor(private http : HttpClient) { }

    addNewUser(email: string, username: string, phone: string, password: string)
    {
        this.url = this.baseUrl + "register";
        return this.http.post(this.url, { "email":email, "name":username, "phone":phone, "password":password} );        
    }

    buyShare(email,id,quantity)
    {
      this.url = this.baseUrl + "buy";
      return this.http.post(this.url, { "email":email, "companyId":id, "quantity":quantity} );               
    }
  

    authenticate(mailid: string, password: string) 
    {    
        this.url = this.baseUrl + "signin";
        return this.http.post<UserResponse>(this.url, { "email":mailid,"password":password} );       
        //return this.http.post<UserResponse>(this.url, { "username":mailid,"password":password} );        
    }
    addtoWatchlist(mailid: String, company_id: number)
    {
      const headers = new Headers();
        headers.append('Access-Control-Allow-Headers', 'Content-Type');
        headers.append('Access-Control-Allow-Methods', 'GET');
        headers.append('Access-Control-Allow-Origin', '*');

      this.url=this.baseUrl+"add-watchlist";
      return this.http.post<UserResponse>(this.url, { "email":mailid,"companyid":company_id} ); 
    }
    /*addWatchList(email,id)
  {
    this.url = this.baseUrl + "add-watchlist";
    return this.http.post<UserResponse>(this.url, {"email":email,"id":id});  
  }*/
  
   

    isUserLoggedIn() {
      let user = localStorage.getItem('username');
      return !(user === null)
    }

    logOut() {
      localStorage.removeItem('username')
    }
 
}
