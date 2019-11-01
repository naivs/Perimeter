import {Component, ElementRef, HostListener, Inject, Input, ViewChild} from '@angular/core';
import {Photo} from "../../photobase.model";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-grid-gallery-item',
  templateUrl: 'grid-item.component.html',
  styleUrls: ['./grid-item.component.scss']
})
export class GridGalleryItemComponent {

  @Input() photo: Photo;
  @Input() rowHeight: number = 1;
  @Input() gutterSize: number = 1;
  @ViewChild('img', {static: true}) img: ElementRef;

  public rows: number = 0;

  constructor(public dialog: MatDialog) {}

  openDialog(photo:Photo): void {
    const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      width: '90%',
      height: '90%',
      data: photo,
      position: {
        top: '30px'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }

  @HostListener('window:resize')
  calculateRows() {
    this.rows = Math.floor(this.img.nativeElement.offsetHeight / (this.rowHeight + this.gutterSize));
  }

  getLocation():string {
    // return window.location.origin;
    return 'http://localhost:8090/';
  }
}

@Component({
  selector: 'dialog-overview-example-dialog',
  templateUrl: 'grid-item.dialog.html',
})
export class DialogOverviewExampleDialog {

  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Photo) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
