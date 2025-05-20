import { Routes } from '@angular/router';
import { LoginComponent } from './pages/menu/loguinRegistro/login/login.component';
import { RegistroComponent } from './pages/menu/loguinRegistro/registro/registro.component';
import { WikiComponent } from './pages/wiki/wiki.component';
import { MenuComponent } from './pages/menu/menu.component';


export const routes: Routes = [
    {path: '', component: MenuComponent},
    {path: 'log', component: LoginComponent},
    {path: 'reg', component: RegistroComponent},
    {path: 'wiki', component: WikiComponent},
];