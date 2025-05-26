import { Routes } from '@angular/router';
import { LoginComponent } from './pages/menu/loguinRegistro/login/login.component';
import { RegistroComponent } from './pages/menu/loguinRegistro/registro/registro.component';
import { WikiComponent } from './pages/wiki/wiki.component';
import { MenuComponent } from './pages/menu/menu.component';
import { PostComponent } from './pages/wiki/post/post.component';
import { PerfilComponent } from './pages/wiki/perfil/perfil.component';
import { AnadirComponent } from './pages/wiki/anadir/anadir.component';
import { redirectionGuardGuard } from './guard/redirection-guard.guard'


export const routes: Routes = [
    {path: '', component: MenuComponent},
    {path: 'log', component: LoginComponent},
    {path: 'reg', component: RegistroComponent},
    {path: 'wiki', component: WikiComponent, canActivate:[redirectionGuardGuard]},
    {path: 'wiki/post/:id', component: PostComponent, canActivate:[redirectionGuardGuard]},
    {path: 'wiki/perfil', component: PerfilComponent, canActivate:[redirectionGuardGuard]},
    {path: 'wiki/anadir', component: AnadirComponent, canActivate:[redirectionGuardGuard]},
];