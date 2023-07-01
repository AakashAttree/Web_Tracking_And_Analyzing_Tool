import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './user/admin/admin.component';
import { CustomerComponent } from './user/customer/customer.component';
import { CompanyConfigComponent } from './user/super-admin/company-config/company-config.component';
import { AddComponent } from './user/super-admin/customer-company/add/add.component';
import { CustomerCompanyComponent } from './user/super-admin/customer-company/customer-company.component';
import { HomeComponent } from './user/super-admin/home/home.component';
import { ReportsComponent } from './user/super-admin/reports/reports.component';
import { SuperAdminComponent } from './user/super-admin/super-admin.component';
import { UserManagementComponent } from './user/super-admin/user-management/user-management.component';
import { UserComponent } from './user/user.component';



const routes: Routes = [
  { path:"user", component: UserComponent, 
  children : [
    { path:"super-admin", component: SuperAdminComponent, children:[
      { path:"company-config", component: CompanyConfigComponent},
      { path:"customer-company", component: CustomerCompanyComponent},
      { path:"home", component: HomeComponent},
      { path:"report", component: ReportsComponent},
      { path:"user-mgmt", component: UserManagementComponent},
    ] },
    { path:"admin", component: AdminComponent},
    { path:"customer", component: CustomerComponent},
    { path:"", component: AdminComponent}
  ]}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
