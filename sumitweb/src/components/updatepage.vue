// QuestionnaireDetail.vue
<script>
import axios from 'axios';
import {ref} from 'vue';
export default {

  data() {
    return {
      showError: false,
      answersString:'',
      title:'',
      questionnaireDetails: [],
      quiz:[],
      changquiz:{},
      user: {
          name: '',
          phoneNumber: '',
          email: '',
          age:0,
          qnId:1,
          qid:1,
          ans:''
        },
      quizAnswers: {},
      quizData : ref({
      questionnaire: {
        id:19,
        title: "今天午餐吃什麼",
        description: "前門還是後門",
        published: false,
        startDate: "2023-11-20",
        endDate: "2023-11-30"
      },
      question_list: [
        {
          qnId:19,
          quId: 1,
          qTitle: "dko",
          optionsType: "選擇題",
          necessary: false,
          options: "麵線羹;煎餃;拉麵"
        }
      ]
    })
    };
  },
  mounted() {
    // 使用 this.$route.params.id 從資料庫中取得詳細資料
    const questionnaireId = this.$route.params.id;
    console.log(questionnaireId);
    const questionnaireId1=62;
    this.fetchQuestionnaireDetails(questionnaireId);

    this.questionnaireId = questionnaireId;
  },
  methods: {
    async cancel(){
      this.$router.push('/updateQuestion'); 
    },

    async deleteQuestionnaire() {
      
      // 彈出確認對話框
      const confirmed = confirm("確定要刪除問卷嗎？刪除後就不可復原");
      
       // 如果用戶確認刪除，執行刪除邏輯
    if (confirmed) {
      try {
        // 獲取要刪除的問卷的 ID
        const questionnaireId = this.questionnaireDetails.id; // 根據你的實際情況獲取 ID

        // 向後端發送刪除請求
        const response = await axios.delete(`http://localhost:8081/quiz/delete?qnId=${questionnaireId}`);

        // 處理刪除操作的響應結果，例如輸出成功消息或導航到其他頁面等
        console.log('Questionnaire deleted:', response);

        // 導航到其他路由，例如回到問卷列表頁面
        this.$router.push('/searchQuestion'); // 根據你的實際情況導航到目標頁面
      } catch (error) {
        console.error('Error deleting questionnaire:', error);
      }
    }
  },
    
    async fetchQuestionnaireDetails(id) {
      try {
        const response = await axios.get(`http://localhost:8081/quiz/questions?qnId=${id}`);
        console.log(response.data);
        if (response.data.questionnaire) {
            // 將後端回傳的資料存儲在 questionnaireDetails 中
            this.questionnaireDetails = response.data.questionnaire;
            this.quiz =  response.data.questions;

        } else {
            console.error('Error: Missing quizVoList property in response:', response.data);
        }
        
        console.log(this.questionnaireDetails);
      } catch (error) {
        console.error('Error getting questionnaire details:', error);
      }
    },
  async updateQuestionnaire(){

  // 隱藏錯誤信息
  this.showError = false;
  
// 處理 quizAnswers，將多個回答內容用分號分隔合成一個字串
Object.keys(this.quizAnswers).forEach((quId) => {
  if (Array.isArray(this.quizAnswers[quId])) {
    this.quizAnswers[quId] = this.quizAnswers[quId].join(';'); // 合成字符串
  } else {
    // 如果不是數組，可以根據實際情況進行處理，例如使用 split 函數
    this.quizAnswers[quId] = this.quizAnswers[quId].split(';').join(';');
  }
});
// 轉換代理對象為字符串
const answersString = Object.values(this.quizAnswers).join(';');
// console.log(answersString);  // 輸出："漢堡王;炒飯"

// 將 quizAnswers 賦值給 answers
this.user.ans = answersString;

//   console.log('Sending data to server:', this.user, this.user.ans); // 添加這行 log 語句
try {
  const updateData = {
    questionnaire: {
          id: this.questionnaireDetails.id,
          title:this.questionnaireDetails.title , // 你可以根据需要更改標題
          description: this.questionnaireDetails.description, // 你可以根据需要更改說明
          published: false, // 你可以根据需要更改發佈狀態
          startDate: this.questionnaireDetails.startDate, // 你可以根据需要更改開始日期
          endDate: this.questionnaireDetails.endDate, // 你可以根据需要更改結束日期
        },
        question_list: this.quiz.map((question) => {
  return {
    qnId: question.qnId,
    quId: question.quId,
    qTitle: question.qTitle,
    optionsType: question.optionsType,
    necessary: question.necessary,
    options: question.options
  };
})
  };

  const responseUpdate = await axios.post('http://localhost:8081/quiz/update', updateData, {
    headers: {
      'Content-Type': 'application/json'
    }
  });
  console.log(responseUpdate);
  // 在這裡處理 responseUpdate，可能包括處理成功或錯誤的邏輯
} catch (error) {
  // 在這裡處理錯誤，例如輸出錯誤信息或執行錯誤處理邏輯
  console.error('Error during update:', error);
}


},
  }
  // 其他組件選項
}
</script>

<template>
    <div  class="form-container">
      <!-- 表單內容 -->
      <h1>更改表單內容</h1>
          <h2>{{ questionnaireDetails.title }}</h2>
      <div>
          <p class="form-description">說明: {{ questionnaireDetails.description }}</p>
          <textarea rows="5" cols="20" name="description" id="description" placeholder="更改問卷說明" required></textarea>
      </div>
      <div>
          <p class="form-date">開始日期: {{ questionnaireDetails.startDate }}</p>          
          更改開始日期<input type="date" id="startDate" name="startDate" required>
      </div>
      <div>
          <p class="form-date">結束日期: {{ questionnaireDetails.endDate }}</p>
          更改結束日期<input type="date" id="endDate" name="endDate" required>
      </div>

      <!-- 顯示問題清單 -->
      <h3  class="question-list-title">問題清單</h3>
      <form @submit.prevent="submitForm" class="question-form">
        <ul class="question-list">
          <li v-for="(question, index) in quiz" :key="index" class="question-item">
            <p class="question-number">第{{ question.quId }}題</p>
            <!-- <p class="question-title">題目: {{ question.qTitle }}</p> -->
            題目:<input type="text" name="changquiz" id="changquiz" placeholder="在這更改問卷問題" v-model="question.qTitle" required >
            <!-- 不同種類的輸入元素，可以根據問題的類型進行選擇 -->
            <div v-if="question.optionsType === '簡答題'" class="options">
              <label class="options-title" for="textAnswer">請回答：</label>
              <!-- <input type="text" name="changquiz" id="changquiz" placeholder="在這更改問卷選項" v-model="changquiz[question.quId]" required > -->
              <input type="text" v-model="quizAnswers[question.quId]" id="textAnswer" class="options-title-text"  required >
            </div>
  
            <div v-if="question.optionsType === '選擇題' && question.options" class="options" style="white-space: pre-line;">
              <p class="options-title">選項：</p>
              <span v-for="(option, optionIndex) in question.options.split(';')" :key="optionIndex" class="option">
                <label class="option-label">
                    <!-- <input type="text" name="changquiz" id="changquiz" placeholder="在這更改問卷選項" v-model="changquiz[question.quId].options[optionIndex]" required > -->
                    <input type="radio" v-model="quizAnswers[question.quId]" :value="option">
                    {{ option }}
                </label>
            </span>
            </div>
  
            <!-- 其他問題相關資訊的顯示 -->
          </li>
        </ul>
        <!-- <button type="submit" class="submit-button">提交表單</button> -->
        <button @click="updateQuestionnaire" class="submit-button">更新問卷</button>
        <button @click="deleteQuestionnaire" class="delete-button">刪除問卷</button>
        <button @click="cancel" class="delete-button">取消更新</button>
      </form>
    </div>
  </template>
  
<style scoped>
  input{
    border: black solid 1px;
    border-radius: 3px;
    margin-left: 5px;
  }
  input[type="text"] {
    border: black solid 1px;
    border-radius: 3px;
    margin-left: 5px;
    margin-right: 5px;
  }
  .contact-info{
    margin-bottom: 2%;
  }
  .delete-button{
    background-color: black;
    color: white;
    border-radius: 5px;
    margin-left: 3%;
    width: 100px;
    height: 43px;

  }
    .form-container {
      max-width: 600px;
      margin: 0 auto;
    }
    
    .form-description {
      color: #555;
    }
    
    .form-date {
      font-style: italic;
    }
    
    .question-list-title {
      margin-top: 20px;
      font-size: 1.5em;
    }
    
    .question-form {
      margin-top: 20px;
    }
    
    .question-list {
      list-style: none;
      padding: 0;
    }
    
    .question-item {
      border: 1px solid #ddd;
      padding: 15px;
      margin-bottom: 15px;
    }
    
    .question-number {
      font-weight: bold;
    }
    
    .question-title {
      margin-bottom: 10px;
    }
    
    .options {
      margin-top: 10px;
    }
    
    .options-title {
      font-weight: bold;
    }
    
    .option {
      display: block;
      margin-bottom: 8px;
    }
    
    .option-label {
      display: flex;
      align-items: center;
    }
    
    .option-input {
      margin-right: 8px;
    }
    
    .submit-button {
      background-color: #4caf50;
      color: white;
      padding: 10px 15px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-size: 1em;
    }
    
    .submit-button:hover {
      background-color: #45a049;
    }
    .options-title-text{
      border: black solid 1px;
      border-radius: 5px;
    }
    p{
        margin-top: 1rem;
        margin-bottom: 1rem;
    }
    </style>