import { Routes } from '@angular/router';
import { LoginComponent } from './pages/menu/loguinRegistro/login/login.component';
import { RegistroComponent } from './pages/menu/loguinRegistro/registro/registro.component';
import { WikiComponent } from './pages/wiki/wiki.component';
import { MenuComponent } from './pages/menu/menu.component';
import { PostComponent } from './pages/wiki/post/post.component';
import { PerfilComponent } from './pages/wiki/perfil/perfil.component';
import { AnadirComponent } from './pages/wiki/anadir/anadir.component';


export const routes: Routes = [
    {path: '', component: MenuComponent},
    {path: 'log', component: LoginComponent},
    {path: 'reg', component: RegistroComponent},
    {path: 'wiki', component: WikiComponent},
    {path: 'wiki/post', component: PostComponent},
    {path: 'wiki/perfil', component: PerfilComponent},
    {path: 'wiki/anadir', component: AnadirComponent}
];