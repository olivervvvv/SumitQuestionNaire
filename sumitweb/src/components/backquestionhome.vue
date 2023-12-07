<script>
    import axios from 'axios';
    import { ref } from 'vue';
    
    export default {
      data() {
        return {
          showError: false,
          questionnaireDetails: {},
          quiz: [],
          quizAnswers: {}, // 用于存储用户的回答
        };
      },
      mounted() {
        // 使用 this.$route.params.id 从数据库中获取详细数据
        const questionnaireId = this.$route.params.id;
        this.fetchQuestionnaireDetails(questionnaireId);
      },
      methods: {
        async fetchQuestionnaireDetails(id) {
          try {
            const response = await axios.get(`http://localhost:8081/quiz/questions?qnId=${id}`);
            if (response.data.questionnaire) {
              // 将后端返回的数据存储在 questionnaireDetails 和 quiz 中
              this.questionnaireDetails = response.data.questionnaire;
              this.quiz = response.data.questions;
            } else {
              console.error('Error: Missing quizVoList property in response:', response.data);
            }
          } catch (error) {
            console.error('Error getting questionnaire details:', error);
          }
        },
        async submitForm() {
          // 处理 quizAnswers，将多个回答内容用分号分隔合成一个字符串
          Object.keys(this.quizAnswers).forEach((quId) => {
            if (Array.isArray(this.quizAnswers[quId])) {
              this.quizAnswers[quId] = this.quizAnswers[quId].join(';'); // 合成字符串
            } else {
              this.quizAnswers[quId] = this.quizAnswers[quId].split(';').join(';');
            }
          });
    
          // 将 quizAnswers 赋值给 answers
          const answersString = Object.values(this.quizAnswers).join(';');
          this.user.ans = answersString;
    
          try {
            // 构造要发送到后端的数据对象
            const updateData = {
              questionnaire: this.questionnaireDetails,
              question_list: this.quiz,
            };
    
            const responseUpdate = await axios.post('http://localhost:8081/quiz/update', updateData, {
              headers: {
                'Content-Type': 'application/json',
              },
            });
    
            console.log(responseUpdate);
            // 在这里处理 responseUpdate，可能包括处理成功或错误的逻辑
          } catch (error) {
            // 在这里处理错误，例如输出错误信息或执行错误处理逻辑
            console.error('Error during update:', error);
          }
        },
      },
    };
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
  
      <!-- 新增聯絡資訊 -->
      <!-- <h3  class="question-list-title">填寫聯絡資訊</h3>
      <div class="contact-info">
        <span>姓名:</span><input type="text" v-model="user.name" name="name" id="name" required>
        <span>手機號碼:</span><input type="text" v-model="user.phoneNumber" name="phoneNumber" id="phoneNumber" required>
      </div>
      <span>電子郵件:</span><input type="text" v-model="user.email" name="email" id="email"> -->

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
    p{
        margin-top: 1rem;
        margin-bottom: 1rem;
    }
    </style>