import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-wiki',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './wiki.component.html',
  styleUrl: './wiki.component.css'
})
export class WikiComponent {

  public test = [0]//[0,1,2,3]

}
