export class User{
id: string = '';
compId: string = '';
firstName: string = '';
lastName: string = '';
userName: string = '';
password: string = '';
email: string = '';
mobile: string = '';
roles:  string[]=[];
createdBy: string = '';
modifiedBy: string = '';
createdTS: Date = new Date();
modifiedTS: Date = new Date();
}