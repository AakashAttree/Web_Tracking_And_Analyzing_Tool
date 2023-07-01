import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  customerInfo: FormGroup;
  constructor() { 
    this.customerInfo = new FormGroup({
      'companyName': new FormControl(null, Validators.required),
      'domainName': new FormControl(null, Validators.required),
      'subDomainName': new FormControl(null, Validators.required),
      'logo': new FormControl(null, Validators.required),
      'email': new FormControl(null,[Validators.required, Validators.email]),
      'contactNumber': new FormControl(null, Validators.required),
      'address': new FormControl(null, Validators.required),
      'state': new FormControl(null, Validators.required),
      'country': new FormControl(null, Validators.required)

    });
  }

  ngOnInit(): void {
  }

  onSubmit(){

  }

}
