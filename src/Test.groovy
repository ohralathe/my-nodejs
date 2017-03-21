/**
 * Created by hoanvo on 1/8/2017.
 */
class Test implements Serializable {
    def ctx

    Test(ctx) {
        this.ctx = ctx
    }

    void printST() {
        this.ctx.sh 'echo "ccccccccccccccccccccccccccccc"'
    }
}

