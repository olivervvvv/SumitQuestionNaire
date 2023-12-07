// QuestionnaireDetail.vue
<script>
import axios from 'axios';

export default {

  data() {
    return {
      showError: false,
      answersString:'',
      title:'',
      questionnaireDetails: [],
      quiz:[],
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
  async submitForm(){
  //   if (!this.user.name.trim() || !this.user.phoneNumber.trim() || !this.user.email.trim()) {
  //   // Show error message
  //   this.showError = true;
  //   return;
  // }
  const confirmed = confirm("問卷內容是否都有填寫了嗎？新增後就不可更改填寫內容");
      
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

  console.log('Sending data to server:', this.user, this.user.ans); // 添加這行 log 語句
  try {
  const responseUser = await axios.post('http://localhost:8081/quiz/setUserList',{
    userList: [
         {
           name: this.user.name,
           phoneNumber: this.user.phoneNumber,
           email: this.user.email,
           age:0,
           qnId:this.questionnaireId,
           qid:1,
           ans:this.user.ans
         }
         ]
        });
    console.log(responseUser.data);
    // 處理從服務器返回的數據，例如，更新前端界面或顯示成功消息
    this.$router.push('/searchQuestion'); 
  }catch(error) {
    console.error('Error submitting quiz:', error);
    // 處理錯誤情況，例如，顯示錯誤消息
  }

},
  }
  // 其他組件選項
}
</script>

<template>
    <div  class="form-container">
      <!-- 表單內容 -->
      <h2>{{ questionnaireDetails.title }}</h2>
      <p class="form-description">說明: {{ questionnaireDetails.description }}</p>
      <p class="form-date">開始日期: {{ questionnaireDetails.startDate }}</p>
      <p class="form-date">結束日期: {{ questionnaireDetails.endDate }}</p>
  
      <!-- 新增聯絡資訊 -->
      <h3  class="question-list-title">填寫聯絡資訊</h3>
      <div class="contact-info">
        <span>姓名:</span><input type="text" v-model="user.name" name="name" id="name" required>
        <span>手機號碼:</span><input type="text" v-model="user.phoneNumber" name="phoneNumber" id="phoneNumber" required>
      </div>
      <span>電子郵件:</span><input type="text" v-model="user.email" name="email" id="email">

      <!-- 顯示問題清單 -->
      <h3  class="question-list-title">問題清單</h3>
      <form @submit.prevent="submitForm" class="question-form">
        <ul class="question-list">
          <li v-for="(question, index) in quiz" :key="index" class="question-item">
            <p class="question-number">第{{ question.quId }}題</p>
            <p class="question-title">題目: {{ question.qTitle }}</p>
            
            <!-- 不同種類的輸入元素，可以根據問題的類型進行選擇 -->
            <div v-if="question.optionsType === '簡答題'" class="options">
              <label class="options-title" for="textAnswer">請回答：</label>
              <input type="text" v-model="quizAnswers[question.quId]" id="textAnswer" class="options-title-text" required >
            </div>
  
            <div v-if="question.optionsType === '選擇題'" class="options" style="white-space: pre-line;">
              <p class="options-title">選項：</p>
              <span v-for="(option, optionIndex) in question.options.split(';')" :key="optionIndex" class="option">
                <label class="option-label">
                    <input type="radio" v-model="quizAnswers[question.quId]" :value="option">
                    {{ option }}
                </label>
            </span>
            </div>
  
            <!-- 其他問題相關資訊的顯示 -->
          </li>
        </ul>
        <button type="submit" class="submit-button">提交表單</button>
      </form>
    </div>
  </template>
  
<style scoped>
  input[type="text"] {
    border: black solid 1px;
    border-radius: 3px;
    margin-left: 5px;
    margin-right: 5px;
  }
  .contact-info{
    margin-bottom: 2%;
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
    </style>