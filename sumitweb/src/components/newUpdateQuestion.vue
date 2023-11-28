<!-- question_list -->
<template>
    <div class="container">
    <div class="title">
        <label for="">我是題目標題</label>
        <button type="submit">創建問卷</button>
    </div>
    <div id="app" class="buildQuestion">
      <form @submit.prevent="createQuiz" class="questionNaireBody">
          <label for="">創建表單題目</label>

          <div v-for="(question, index) in quizData.question_list" :key="index">
              <div>
                  <label :for="'qTitle_' + index">問題標題:</label>
                  <input :id="'qTitle_' + index" type="text" v-model="question.qTitle" required placeholder="請輸入問題標題"> 
              </div>

              <div>
                  <label :for="'optionsType_' + index">問題種類:</label>
                  <select :id="'optionsType_' + index" v-model="question.optionsType" required>
                      <option v-for="type in optionTypes" :key="type" :value="type">{{ type }}</option>
                  </select>
                  <label :for="'necessary_' + index">必填:</label>
                  <input :id="'necessary_' + index" type="checkbox" v-model="question.necessary" class="checkbox">
              </div>    

              <div>
                  <label :for="'options_' + index">Options:</label>
                  <input :id="'options_' + index" type="text" v-model="question.options" required>
              </div>
          </div>
          
          <button type="submit">創建問卷</button>
      </form>
  </div>
    </div>
</template>

<script setup>
    import { ref } from 'vue';
    import axios from 'axios';

    const optionTypes = ["選擇題", "簡答題"];
    
    const addOption = (questionIndex) => {
  quizData.value.question_list[questionIndex].options.push('');
};

    const quizData = ref({
      questionnaire: {
        title: '',
        description: '',
        published: false,
        startDate: '2023-11-20',
        endDate: '2023-11-30',
    },
    question_list: [
        {
          quId: '1',
          qTitle: '1',
          optionsType: optionTypes[0],
          necessary: false,
          options: '><',
        },
        // Add more questions as needed
      ],
    });
    
    const createQuiz = () => {
      // Send POST request to the backend
      axios.post('http://localhost:8081/quiz/createQuestion', quizData.value)
        .then(response => {
          // Handle the response from the server
          console.log(response.data);
          // You can update the UI or perform other actions as needed
        })
        .catch(error => {
            // Handle errors
          console.error('Error creating quiz:', error);
        });
    };
    const getDataFromBackend = async () => {
        try {
            const response = await axios.get('http://localhost:8081/quiz/search',{
        params: {
        // Add your search parameters here
        title: searchData.value.title,
        startDate: searchData.value.startDate,
        endDate: searchData.value.endDate,
        
        },
    });
    console.log('Data from backend:', response.data);
    // Update quizData with the data received from the backend
    Object.assign(quizData.value, response.data);
} catch (error) {
    console.error('Error getting data from backend:', error.response.data); // 打印错误信息
}

};

</script>

<style>
    .container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh; /* 調整高度至適當的值 */
}
    .title{
        display: flex;
        align-items: center;
        background-color: rgb(230, 173, 99);
        justify-content: space-around;
        font-size: 13pt;
          font-weight: 600;
        margin-top: 3%;
        border-radius: 10px;
          width: 70%;
          height: 20%;
    }
.buildQuestion{
          display: flex;
          justify-content: center;
          align-items: center;
          height: 90vh;
          width: 70%;
        } 
        .questionNaireBody{
          display: flex;
          flex-direction: column;
          align-items: center;
          background-color: rgb(230, 173, 99);
          border-radius: 5%;
          width: 100%;
          height: 80%;
        }
        .questionNaireBody label{
          margin-top: 2%;
          margin-bottom: 3%;
          font-size: 13pt;
          font-weight: 600;
        }
        .questionNaireBody input{
          margin-bottom: 5%;
          border: black solid 1px;
          border-radius: 3px;
          width: 100%;
        }
        .checkbox{
          width: 50px;
          height: auto;
        }
        .questionNaireBody button{
          margin-bottom: 2%;
          background-color: rgb(158, 90, 1);
          width: 15%;
          height: 11%;
          font-size: 12pt;
          border-radius: 5%;
          color: #dddddd;
        }
        .description{
          margin-bottom: 5%;
          width: 80%;
          height: 50%;
          border: black solid 1px;
          border-radius: 3px;
        }
        .options-row {
            display: flex;
            align-items: center;
            width: 100%;
        }
        label{
            display: inline;
            white-space: nowrap;
            margin-right: 5px;
        }
        .necessary{
            width: auto;

        }
</style>