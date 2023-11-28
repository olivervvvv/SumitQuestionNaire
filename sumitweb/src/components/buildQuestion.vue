<script setup>
    import { ref } from 'vue';
    import axios from 'axios';
    
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
          optionsType: '有料',
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
    
<template>
      <router-view></router-view>

        <div id="app" class="buildQuestion">
            <form @submit.prevent="createQuiz" class="questionNaireBody">
              <!-- <form class="questionNaireBody"> -->
              <!-- questionnaire -->
              
                <label for="title">問卷標題:</label>
                <input type="text" v-model="quizData.questionnaire.title" required>
             
                <label for="description">問卷說明:</label>
                <textarea rows="5" cols="20" class="description" v-model="quizData.questionnaire.description" required>問卷在此說明</textarea>
                <!-- <input type="text" class="description" v-model="quizData.questionnaire.description" required> -->
        
              <!-- question_list -->
              <!-- <div v-for="(question, index) in quizData.question_list" :key="index">
                <div>
                  <label :for="'qTitle_' + index">Question Title:</label>
                  <input :id="'qTitle_' + index" type="text" v-model="question.qTitle" required>
                </div>
        
                <div>
                  <label :for="'optionsType_' + index">Options Type:</label>
                  <input :id="'optionsType_' + index" type="text" v-model="question.optionsType" required>
                </div>
        
                <div>
                  <label :for="'necessary_' + index">Necessary:</label>
                  <input :id="'necessary_' + index" type="checkbox" v-model="question.necessary">
                </div>
        
                <div>
                  <label :for="'options_' + index">Options:</label>
                  <input :id="'options_' + index" type="text" v-model="question.options" required>
                </div>
              </div> -->
        
              <button type="submit">新增問卷</button>
            </form>
            <!-- <button @click="getDataFromBackend">Get Data from Backend</button> -->
          </div>
          
    </template>
    
    <style>
        * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
    }
      table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }
        .buildQuestion{
          display: flex;
          justify-content: center;
          align-items: center;
          height: 90vh;
          width: 100vw;
        } 
        .questionNaireBody{
          display: flex;
          flex-direction: column;
          align-items: center;
          background-color: rgb(230, 173, 99);
          border-radius: 5%;
          width: 70%;
          height: 70%;
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
</style>