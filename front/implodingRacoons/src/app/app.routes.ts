import { Routes } from '@angular/router';
import { LoginComponent } from './menu/loguinRegistro/login/login.component';
import { RegistroComponent } from './menu/loguinRegistro/registro/registro.component';

export const routes: Routes = [
    {path: 'log', component: LoginComponent},
    {path: 'reg', component: RegistroComponent},
];
