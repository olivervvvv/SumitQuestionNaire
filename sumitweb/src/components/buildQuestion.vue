<script setup>
    import { ref } from 'vue';
    import axios from 'axios';
    import { RouterLink, RouterView, useRouter } from 'vue-router';
    import { getCurrentInstance } from 'vue';

    const optionTypes = ["選擇題", "簡答題"];
    const showError = ref(false);
    // const addOption = (questionIndex) => {
//   quizData.value.question_list[questionIndex].options.push('');
// };
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
          qTitle: '',
          optionsType: optionTypes[0],
          necessary: false,
          options: '',
          inputs: [{ text: '' }],
        },
        // Add more questions as needed
      ],
    });
    const createQuiz = () => {
  if (quizData.value.question_list.some(question => !question.qTitle.trim())) {
    // Show error message
    showError.value = true;
    return;
  }

  //   const createQuiz = ()=> {

  //     if (inputs.value.some(input => !input.text.trim())) {
  //   // 顯示錯誤信息
  //   showError.value = true;
  //   return;
  // }

  // 隱藏錯誤信息
  showError.value = false;
    quizData.value.question_list.forEach(question => {
    // 將 inputs 數組中每個 text 屬性連接成字符串，用分號分隔
    question.options = question.inputs.map(input => input.text).join(';');
  });
  const confirmed = confirm("確定要新增問卷嗎？");

  console.log('Sending data to server:', quizData.value); // 添加這行 log 語句

  axios.post('http://localhost:8081/quiz/createQuestion', quizData.value)
    .then(response => {
      console.log(response.data);
      
      resetForm();
    })
    .catch(error => {
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
    console.error('Error getting data from backend:', error.response.data); // 打印錯誤信息
  }

};
// const deleteTransaction=(index) =>{
//         // 從 arr 數組中刪除指定索引的交易事務
//         // 通過 Vue 提供的 `value` 屬性獲取數組
//         const inputsArray = inputs.value;

//         // 判斷索引是否在有效範圍內
//         if (index >= 0 && index < inputsArray.length) {
//         // 從數組中刪除指定索引的交易事務
//         inputsArray.splice(index, 1);
//         console.log("Deleted transaction at index", index);
//       }else {
//     console.warn("Invalid index for deleting transaction");
//       }
//         };

const deleteTransaction = (questionIndex, inputIndex) => {
  const question = quizData.value.question_list[questionIndex];
  question.inputs.splice(inputIndex, 1);
};
const inputs = ref([]);

const addInput = (questionIndex) => {
  // quizData.value.question_list[questionIndex].inputs.push({ text: '' });
  const question = quizData.value.question_list[questionIndex];
  question.inputs.push({ text: '' });

  // 通過將來自 inputs 的文本連接起來，更新 options 字符串
  question.options = question.inputs.map(input => input.text).join(';');
};
//新增題目
const addQuestion = () => {
  const lastQuestion = quizData.value.question_list[quizData.value.question_list.length - 1];
  const newQuestion = {
    quId: (parseInt(lastQuestion.quId) + 1).toString(),
    qTitle: '',
    optionsType: optionTypes[0],
    necessary: false,
    inputs: [{ text: '' }],
  };
  quizData.value.question_list.push(newQuestion);
};
const resetForm = () => {
  // 重置表單數據
  quizData.value.questionnaire.title = '';
  quizData.value.questionnaire.description = '';
  quizData.value.questionnaire.startDate = '2023-11-20';
  quizData.value.questionnaire.endDate = '2023-11-30';

  // 清空問題列表
  quizData.value.question_list.forEach(question => {
    question.qTitle = '';
    question.optionsType = optionTypes[0];
    question.necessary = false;
    question.options = '';
  });

  // 清空輸入框數組
  inputs.value = [];
};

</script>
    
<template>
      <router-view></router-view>

        <div id="app" class="buildQuestion">
            <form @submit.prevent="createQuiz" @keydown.enter.prevent="" class="questionNaireBody">
              <!-- <form class="questionNaireBody"> -->
              <!-- questionnaire -->
              
                <label for="title">問卷標題:</label>
                <input type="text" v-model="quizData.questionnaire.title" required>
             
                <label for="description">問卷說明:</label>
                <textarea rows="5" cols="20" class="description" v-model="quizData.questionnaire.description" required>問卷在此說明</textarea>
                <!-- <input type="text" class="description" v-model="quizData.questionnaire.description" required> -->
        
                <div class="date-group">
                  <label for="startDate">開始日期：</label>
                  <input type="date" v-model="quizData.questionnaire.startDate" id="startDate" name="startDate" required>
  
                  <label for="endDate">結束日期：</label>
                  <input type="date" v-model="quizData.questionnaire.endDate" id="endDate" name="endDate" required>
                </div>
                
            </form>
            <!-- <button @click="getDataFromBackend">Get Data from Backend</button> -->
          </div>
          <div id="app" class="buildQuestion">
            <form @submit.prevent="createQuiz" @keydown.enter.prevent="" class="questionNaireBody2">
                <label for="">創建表單題目</label>
      
                <div v-for="(question, index) in quizData.question_list" :key="index">
                    <div class="inputbox-group">
                        <label :for="'qTitle_' + index">問題標題:</label>
                        <input :id="'qTitle_' + index" type="text" v-model="question.qTitle" required placeholder="請輸入問題標題"> 
                        
                        <label :for="'optionsType_' + index">問題種類:</label>
                        <select :id="'optionsType_' + index" v-model="question.optionsType" required>
                            <option v-for="type in optionTypes" :key="type" :value="type">{{ type }}</option>
                        </select>
                    </div>
                    
                      <div class="checkbox-group">
                        <label :for="'necessary_' + index">必填:</label>
                        <input :id="'necessary_' + index" type="checkbox" v-model="question.necessary" style="width: 50px; height: 20px;">
                        <button type="button" class="optionsButton" @click="addInput(index)">新增選項</button>
                      <div class="button-container">
                          <!-- <input :id="'options_' + index" class="options" type="text" v-model="question.options" required> -->
                      </div>
                    </div>
                      <label :for="'options_' + index">問題選項:</label>
                    <div class="newInputbox-group">
                      <div class="quizInput" v-for="(input, inputIndex) in question.inputs" :key="inputIndex">
                        <input v-model="input.text" />
                        <button @click="deleteTransaction(index, inputIndex)">Delete</button>
                      </div>
                      
                    </div>
                </div>
                <button type="button" class="complete" @click="addQuestion">下一題</button>
                <!-- <button type="button" class="next">下一頁</button> -->
                <button type="submit" class="endSumit">新增問卷</button>
            </form>
        </div>
    </template>
    
    <style>
        * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
    }
    .newInputbox-group{
      margin-bottom: 5%;
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
          height: 70vh;
          width: 98vw;
        } 
        .questionNaireBody{
          display: flex;
          flex-direction: column;
          align-items: center;
          background-color: rgb(230, 173, 99);
          border-radius: 5%;
          width: 80%;
          min-height: 90%;
          max-height: 90%; /* 初始高度 */
          overflow-y: auto; /* 當內容溢出時顯示滾動條 */
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
        
.questionNaireBody2{
          position: relative;
          display: flex;
          flex-direction: column;
          align-items: center;
          background-color: rgb(230, 173, 99);
          border-radius: 5%;
          width: 80%;
          min-height: 90%;
          max-height: 90%; /* 初始高度 */
          overflow-y: auto; /* 當內容溢出時顯示滾動條 */
        }
        .questionNaireBody2 label{
          margin-top: 2%;
          margin-bottom: 3%;
          font-size: 13pt;
          font-weight: 600;
        }
        .questionNaireBody2 input{
          margin-bottom: 5%;
          border: black solid 1px;
          border-radius: 3px;
        }
        .questionNaireBody2 button{
          margin-bottom: 2%;
          background-color: rgb(158, 90, 1);
          width: 15%;
          height: 11%;
          font-size: 12pt;
          border-radius: 5%;
          color: #dddddd;
        }
        button.optionsButton{
        width: 100px;
      }
      .button-container {
  position: absolute;
  bottom: 48%;  
  right: 50%;
  width: 100px;
}
button.complete{
  position: fixed;
  bottom: 15%;  
  right: 18%;
  width: 80px;
  height: 40px;
}
button.endSumit{
  position: fixed;
  bottom: 7%;  
  right: 18%;
  width: 80px;
  height: 40px;
}
</style>