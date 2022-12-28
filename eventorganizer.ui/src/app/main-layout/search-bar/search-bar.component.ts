import { Component, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

/**
 * @title Basic Inputs
 */
@Component({
  selector: 'search-bar',
  styleUrls: ['search-bar.component.css'],
  templateUrl: 'search-bar.component.html',
})
export class SearchBar {
    input = new FormControl('');

    search: FormGroup = new FormGroup({
        input: this.input
    })

    @Output() searchInput = new EventEmitter<any>(); 

    onSearch() {
        this.searchInput.emit(this.input.value);
    }

  openDialog(): void {
    // const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
    //   data: {name: this.name, animal: this.animal},
    // });

    // dialogRef.afterClosed().subscribe(result => {
    //   console.log('The dialog was closed');
    //   this.animal = result;
    // });
  }
}