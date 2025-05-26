import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { inject } from '@angular/core';

export const redirectionGuardGuard: CanActivateFn = (route, state) => {

      // Inyectamos servicios
    const authService = inject(AuthService);
    const router = inject(Router);

    // Opción sin observable
    if (!authService.existeUsuario()) {
      // Navegamos al login indicando que después redireccione a donde queríamos ir en un principio
      router.navigate([''], { queryParams: { redirectTo: state.url }});
    }

    return authService.existeUsuario();

};
