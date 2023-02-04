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
  currentChipSelected: string;

  search: FormGroup = new FormGroup({
    input: this.input,
  });

  @Output() searchInput = new EventEmitter<any>();

  onSearch() {
    this.searchInput.emit(this.input.value);
  }

  onChipSelected(chipSelected: string) {
    if (this.currentChipSelected === chipSelected) {
      this.currentChipSelected = '';
    } else {
      this.currentChipSelected = chipSelected;
    }

    this.searchInput.emit(this.currentChipSelected);
  }

  openDialog(): void {
    // TODO: Add Advanced Search Dialog
  }
}
