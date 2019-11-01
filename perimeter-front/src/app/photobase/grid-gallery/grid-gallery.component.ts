import {Component, Input} from '@angular/core';
import {Photo} from "../photobase.model";

@Component({
  selector: 'app-grid-gallery',
  templateUrl: './grid-gallery.component.html'
})
export class GridGalleryComponent{

  @Input() photos: Photo[];
  @Input() cols: number = 5;
  @Input() rowHeight: number = 1;
  @Input() gutterSize: number = 1;
}
