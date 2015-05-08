/**
 * @author orleando Dassi
 * @name RoundAmount
 * @date 08/05/2015
 *
 */

function RoundAmount(amount) {

    this.amount = amount;
    this.nbreIncrease = 0;
    this.finalAmount = 0;

}

/**
 * function to check if amount is multiple of 5
 * else increase it for the next multiple of 5
 */

RoundAmount.prototype.multipleOfFive = function() {
    var isMultiple = false;

    while (!isMultiple) {
        if ((this.amount % 5 == 0)) {
            isMultiple = true;
        }
        else {
            this.nbreIncrease++;
        }
    }

    this.amount += this.nbreIncrease;
}