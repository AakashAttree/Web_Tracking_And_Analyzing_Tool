import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { UserComponent } from './user/user.component';



import { AdminComponent } from './user/admin/admin.component';
import { CustomerComponent } from './user/customer/customer.component';
import { SuperAdminComponent } from './user/super-admin/super-admin.component';
import { HomeComponent } from './user/super-admin/home/home.component';
import { UserManagementComponent } from './user/super-admin/user-management/user-management.component';
import { CustomerCompanyComponent } from './user/super-admin/customer-company/customer-company.component';
import { VariablesComponent } from './user/admin/variables/variables.component';
import { TrackingCodeComponent } from './user/admin/tracking-code/tracking-code.component';
import { AddConfigComponent } from './user/super-admin/company-config/add-config/add-config.component';
import { EditConfigComponent } from './user/super-admin/company-config/edit-config/edit-config.component';
import { DeleteConfigComponent } from './user/super-admin/company-config/delete-config/delete-config.component';
import { CompanyConfigComponent } from './user/super-admin/company-config/company-config.component';
import { AddComponent } from './user/super-admin/customer-company/add/add.component';
import { EditComponent } from './user/super-admin/customer-company/edit/edit.component';
import { DeleteComponent } from './user/super-admin/customer-company/delete/delete.component';
import { AddUserComponent } from './user/super-admin/user-management/add-user/add-user.component';
import { EditUserComponent } from './user/super-admin/user-management/edit-user/edit-user.component';
import { DeleteUserComponent } from './user/super-admin/user-management/delete-user/delete-user.component';
import { AddTrackingCodeComponent } from './user/admin/tracking-code/add-tracking-code/add-tracking-code.component';
import { EditTrackingCodeComponent } from './user/admin/tracking-code/edit-tracking-code/edit-tracking-code.component';
import { DeleteTrackingCodeComponent } from './user/admin/tracking-code/delete-tracking-code/delete-tracking-code.component';
import { AddVariableComponent } from './user/admin/variables/add-variable/add-variable.component';
import { EditVariableComponent } from './user/admin/variables/edit-variable/edit-variable.component';
import { DeleteVariableComponent } from './user/admin/variables/delete-variable/delete-variable.component';
import { CommonComponent } from './user/super-admin/customer-company/common/common.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    UserComponent,
    AdminComponent,
    CustomerComponent,
    SuperAdminComponent,
    HomeComponent,
    UserManagementComponent,
    CustomerCompanyComponent,
    VariablesComponent,
    TrackingCodeComponent,
    AddConfigComponent,
    EditConfigComponent,
    DeleteConfigComponent,
    CompanyConfigComponent,
    AddComponent,
    EditComponent,
    DeleteComponent,
    AddUserComponent,
    EditUserComponent,
    DeleteUserComponent,
    AddTrackingCodeComponent,
    EditTrackingCodeComponent,
    DeleteTrackingCodeComponent,
    AddVariableComponent,
    EditVariableComponent,
    DeleteVariableComponent,
    CommonComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
