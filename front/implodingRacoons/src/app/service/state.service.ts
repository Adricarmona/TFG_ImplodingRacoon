import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StateService {

  private estadoActual = new BehaviorSubject<'menu' | 'registro' | 'login'>('login');
  estado$ = this.estadoActual.asObservable();

  cambiarEstado(nuevoEstado: 'menu' | 'registro' | 'login') {
    this.estadoActual.next(nuevoEstado);
  }
}