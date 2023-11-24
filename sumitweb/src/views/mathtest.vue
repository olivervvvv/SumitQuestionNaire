<script>
    // import HelloWorld from './components/HelloWorld.vue'
    export default {
      data() {
        return {
          plusNum: 0,
          neNum: 0,
          totalNum: 0,
          arr: [],
          newTransaction: { // Add a data object for new transactions
            name: "",
            money: 0
          }
        }
      },
      methods: {
        cool() {
          let obj = {
            name: "cool",
            money: 100
          }
    
          let obj2 = {
            name: "cool",
            money: -50
          }
          this.arr.push(obj)
          this.arr.push(obj2)
          
          this.calculateBalance();
        },
        deleteTransaction(index) {
        // 從 arr 數組中刪除指定索引的交易事務
        this.arr.splice(index, 1);
        // 刪除交易後重新計算餘額
        this.calculateBalance();

        console.log("Deleted transaction at index", index);
        },
      
        addTransaction() {
          // Add a new transaction to the arr array
          this.arr.push({
            name: this.newTransaction.name,
            money: parseFloat(this.newTransaction.money) // Convert input to a number
          });
    
          // Reset the input fields
          this.newTransaction.name = "";
          this.newTransaction.money = 0;
    
          // Calculate plusNum, neNum, and totalNum
          this.calculateBalance();
        },
    
        calculateBalance() {
          // Recalculate plusNum, neNum, and totalNum based on the arr
          this.plusNum = this.arr.filter(item => item.money > 0)
            .reduce((sum, item) => sum + item.money, 0);
          this.neNum = this.arr.filter(item => item.money < 0)
            .reduce((sum, item) => sum + item.money, 0);
          this.totalNum = this.plusNum + this.neNum;
        },
        logout(){
            this.$router.push({ name: 'logi' });
        }
      },

      // components: {
      //   HelloWorld
      // }
    }
    </script>
    
    <template>
      <div class="bgArea">
        <div class="blueArea">
          <!-- <h1>{{ totalNum }}</h1> -->
          <h1>Expense Tracker</h1>
          <h2>Kouhei</h2>
          <h5>YOUR BALANCE</h5>
          <span>$ {{totalNum}}</span>
          <div class="sign">
            <button type="button" @click="logout">登出</button>
          </div>
        </div>
        <div class="whiteArea">
          <div class="top">
            <h1 class="wh-titleI">INCOME</h1>
            <h1 class="wh-titleE">EXPENSE</h1>
          </div>
          <div class="textitle">
            <h2 class="totalIncom">$ {{ plusNum }}</h2>
            <h2 class="toatalExpense">$ {{ neNum }}</h2>
          </div>
          <p></p>
          <!-- <div class="btn">
            <button type="button" class="numBtn" v-on:click="cool">Add transaction</button>
          </div> -->
          <div class="transaction-input">
            <input type="text" v-model="newTransaction.name" placeholder="Transaction Name">
            <input type="number" v-model="newTransaction.money" placeholder="Transaction Amount">
            <button type="button" class="numBtn" v-on:click="addTransaction">Add</button>
          </div>
          <div class="content">
            <div class="block" v-for="(item, index) in arr" :key="index">
              <span>{{ item.name }}</span>
              <span>{{ item.money }}</span>
              <button @click="deleteTransaction(index)">Delete</button>
            </div>
          </div>
        </div>
      </div>
      
    </template>
    
    <style lang="scss" scoped>
    .transaction-input{
      margin-top: 30px;
    }
    .sign{
      position: absolute;
      bottom: 10px;
      left: 10px;
      background-color: rgb(128, 179, 179);
      border-radius: 5px;
    }
    .bgArea {
      width: 100%;
      height: 100vh;
      display: flex;
    
      .blueArea {
        width: 40vw;
        height: 100vh;
        background-color: #4b85a0;
        h1{
          padding-top: 10%;
          color: aliceblue;
          display: flex;
          top: 10%;
          justify-content: center;
        }
        h2{
          padding-top: 10%;
          color: aliceblue;
          display: flex;
          top: 10%;
          justify-content: center;
        }
        h5{
          padding-top: 5%;
          color: aliceblue;
          display: flex;
          top: 10%;
          justify-content: center;
        }
        span{
          font-size: 35px;
          color: aliceblue;
          display: flex;
          justify-content: center;
        }
      }
      .top{
        width: 100%;
        display: flex;
        justify-content: flex-end;
      }
    
      .wh-titleI{
        color: red;
        width: 35%;
        padding-left: 3%;
      }
      .wh-titleE{
        color: green;
        width: 45%;
        padding-right: 20%;
      }
      .textitle{
        display: flex;
        justify-content: center;
      }
    .totalIncom{
        padding-left: 10%;
        height: 30px;
        width: 150px;
        margin: 0;
        color: red;
      }
      .toatalExpense{
        padding-left: 10%;
        height: 30px;
        width: 150px;
        margin: 0;
        color: green;
      }
    
      .whiteArea {
        width: 60vw;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction:column;
        .content{
          width:60% ;
          height: 40vh;
          margin-top: 50px;
          .block{
            display: flex;
            justify-content: space-between;
            font-size: 24pt;
            border: 1px solid black;
            margin-bottom: 10px;
            border-radius: 5px;
          }
        }
      }
    
    }
    </style>
    