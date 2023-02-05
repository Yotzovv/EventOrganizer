export class Star {
    id: number;
    filled: boolean;
}

export class StarRating {
    stars: Array<Star> = [];

    constructor(numOfFilledStars = 0) {
      console.log({
        numOfFilledStars
      })
      for (let i = 1; i <= 5; i++) {
        this.stars.push({
          id: i,
          filled: numOfFilledStars > 0,
        });
        numOfFilledStars -= 1;
        console.log(numOfFilledStars > 0)
      }
    }

    starClicked(star: Star) {
        const strIdx: number = this.stars.map(s => s.id).indexOf(star.id);
        if (star.filled) {
            this.stars.forEach(s => {
                s.filled = false;
            });
        }
        this.stars.slice(0, strIdx+1).forEach((star: Star) => {
            star.filled = true;
        });
    }

    getNumberRating(): number {
        return this.stars.filter(s => s.filled).length;
    }
}