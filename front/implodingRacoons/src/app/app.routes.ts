import { Routes } from '@angular/router';
import { LoginComponent } from './pages/menu/loguinRegistro/login/login.component';
import { RegistroComponent } from './pages/menu/loguinRegistro/registro/registro.component';
import { WikiComponent } from './pages/wiki/wiki.component';
import { MenuComponent } from './pages/menu/menu.component';
import { PostComponent } from './pages/wiki/post/post.component';
import { PerfilComponent } from './pages/wiki/perfil/perfil.component';
import { AnadirComponent } from './pages/wiki/anadir/anadir.component';
import { redirectionGuardGuard } from './guard/redirection-guard.guard'
import { AjustesComponent } from './pages/menu/ajustes/ajustes.component';
import { SobreNosotrosComponent } from './pages/menu/sobre-nosotros/sobre-nosotros.component';
import { CrearMesaComponent } from './pages/menu/crear-mesa/crear-mesa.component';
import { UnirseComponent } from './pages/menu/unirse/unirse.component';
import { MesaComponent } from './pages/mesa/mesa.component';


export const routes: Routes = [
    {path: '', component: MenuComponent},
    {path: 'log', component: LoginComponent},
    {path: 'reg', component: RegistroComponent},
    {path: 'ajustes', component: AjustesComponent},
    {path: 'unirMesa', component: UnirseComponent, canActivate:[redirectionGuardGuard]},
    {path: 'crearMesa', component: CrearMesaComponent, canActivate:[redirectionGuardGuard]},
    {path: 'sobre_nosotros', component: SobreNosotrosComponent},
    {path: 'wiki', component: WikiComponent, canActivate:[redirectionGuardGuard]},
    {path: 'wiki/post/:id', component: PostComponent, canActivate:[redirectionGuardGuard]},
    {path: 'wiki/perfil/:id', component: PerfilComponent, canActivate:[redirectionGuardGuard]},
    {path: 'wiki/anadir', component: AnadirComponent, canActivate:[redirectionGuardGuard]},
    {path: 'partida/:id', component: MesaComponent, canActivate:[redirectionGuardGuard]},
];