import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userInfo:{ type :string, roles: string[], name:string} = 
  { type :'sa', 
  roles: ['ROLE_SA'], 
  name:'string'};
  
  constructor() { }

  public getUserInfo(){return this.userInfo}
}
