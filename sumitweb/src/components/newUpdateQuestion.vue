<script>
  import axios from 'axios';
  import moment from 'moment';
  //import { format } from 'date-fns';
  import { parseISO, format } from 'date-fns';
export default {
  data() {
      return {
          questArrLocal: JSON.parse(localStorage.getItem("questArrLocal")) || [],

          questionName: '',
          description: '',
          startTime: '',
          endTime: '',
          questArr: [],
          selectedQuestionType: "radio",
          questionTypes: ["radio", "checkbox", "text"],
          currentQuid: 1,

          questionnaire:{
              title:'',
              description:'',
              published:false,
              startDate:'',
              endDate:'',
          },
          questionList:[
              {
              quid:1,
              qTitle:'',
              optionType:'',
              necessary:false,
              option:''
              },

          ]








      }
  },
  mounted() {
      this.editQuestionnaire(this.$route.params.quizId);
      this.geteEditQuestionnaireTitle(this.$route.params.quizId);


      },
  methods: {
      async editQuestionnaire(quizId) {
          // 将quizId转为整数
          const parsedQuizId = parseInt(quizId, 10);
          try {
              const response = await axios.get(`http://localhost:8081/api/quiz/search_question_list/${quizId}`);
              const quizData = response.data; // 这里假设后端返回的数据包含问卷的所有信息
              // 将问卷数据填充到编辑相关的变量中
              this.editingQuizId = parsedQuizId;
              // 填充其他问卷数据...
              console.log("quizData: ",quizData);
              console.log("quizId: ",this.editingQuizId );
              // 清空questArr以防止重複填充
              this.questArr = [];
              // 遍歷資料陣列，創建新的題目對象，並填充到questArr中
              for (let i = 0; i < quizData.questionList.length; i++) {
                  const questItem = quizData.questionList[i];
                  // 確保 optionsArray 是一個陣列
                  const optionsArray = questItem.option ? questItem.option.split(',') : [];

                  const newQuestion = {
                      quid: questItem.quid,
                      questionType: questItem.optionType, // 假設 optionType 就是你的 questionType
                      question: questItem.qTitle,
                      options: optionsArray.map(optionText => ({
                          selected: false, // 或者根據需要初始化為true
                          text: optionText
                      })),
                  };
                  this.questArr.push(newQuestion);
              }
          } catch (error) {
              console.error('Error fetching quiz data:', error);
          }
      },
      async geteEditQuestionnaireTitle(quizId) {
          try {
              const response = await axios.get(`http://localhost:8081/api/quiz/search_questionnaire_by_id/${quizId}`);
              const questionnaireDetile = response.data; // 这里假设后端返回的数据包含问卷的所有信息
              // 填充其他问卷数据...
              console.log("questionnaireDetile: ",questionnaireDetile);
              this.questionName = questionnaireDetile.questionnaire.title;
              this.description= questionnaireDetile.questionnaire.description;
              const startDate = parseISO(questionnaireDetile.questionnaire.startDate);
              const formattedStartDate = format(startDate, 'yyyy-MM-dd');
              this.startTime = formattedStartDate;
              const endDate = parseISO(questionnaireDetile.questionnaire.endDate);
              const formattedEndDate = format(endDate, 'yyyy-MM-dd');
              this.endTime = formattedEndDate;
          } catch (error) {
              console.error('Error fetching quiz data:', error);
          }
      },
      handleQuestionTypeChange(questionIndex) {
          const selectedQuestionType = this.questArr[questionIndex].questionType;
          // 清除其他已有的選項
          this.questArr[questionIndex].options = [];

          if (selectedQuestionType === 'text') {
              // 如果選擇簡答題，執行相應的邏輯
              this.createNewOptions(questionIndex);
              this.questArr[questionIndex].options[0].text = '請在此輸入回答';
          }
      },
      createNewQuest() {
          const newQuestion = {
          quid: this.questArr.length + 1,  // 使用题目的索引作为 quid
          questionType:'radio',
          question: '',
          options: [],
      };
      this.questArr.push(newQuestion);
      },
      createNewOptions(questionIndex) {
          const currentQuestion = this.questArr[questionIndex];
          // 檢查是否為簡答題
          if (currentQuestion.questionType === 'text') {
              if (currentQuestion.options.length > 0) {
                  alert('簡答題只能有一個選項！');
                  return;
              }
              const newOption = {
                  quid: currentQuestion.quid,
                  qTitle: currentQuestion.question,
                  optionType: currentQuestion.questionType,
                  necessary: false,
                  option: ['請在此輸入回答'],  // 簡答題只有一個選項
              };
              currentQuestion.options.push(newOption);
              // 簡答題只保留一個選項，直接返回
              return;
          }
          // 檢查 currentQuestion 和 options 是否存在
          if (currentQuestion && currentQuestion.options) {
              const newOption = {
                  quid: currentQuestion.quid,
                  qTitle: currentQuestion.question,
                  optionType: currentQuestion.questionType,
                  necessary: false,
                  option: currentQuestion.options.map(option => option.text),
              };
              currentQuestion.options.push(newOption);
          } else {
              console.error('Question or options are undefined.');
          }
          console.log('quid:', currentQuestion.quid)
          console.log('qTitle:', currentQuestion.question)
          console.log('optionType:', currentQuestion.questionType); 
          console.log('option:', currentQuestion.options.map(option => option.text))
      },
      deleteNewQuest(questionIndex) {
          if (this.questArr.length > 1) {
              this.questArr.splice(questionIndex, 1);
              this.questArr.forEach((question, index) => {
              question.quid = index + 1;
          });
          } else {
              alert("至少需要保留一個問題。");
          }
      },
      deleteNewOptions(questionIndex, optionIndex) {
          this.questArr[questionIndex].options.splice(optionIndex, 1);
      },



















      saveToDB(questionIndex){
                      // 檢查問卷名稱是否有值
                      if (!this.questionName || !this.questionName.trim()) {
              alert('請輸入問卷名稱！');
              return;
          }
          // 檢查描述內容是否有值
          if (!this.description || !this.description.trim()) {
              alert('請輸入描述內容！');
              return;
          }
          // 檢查問卷開始時間是否有值且在現在之後
          if (!this.startTime) {
              alert('請選擇問卷開始時間！');
              return;
          }
          const currentDateTime = new Date();
          const startDateTime = new Date(this.startTime);
          // 將現在的時間設置為今天的 00:00:00
          const todayStart = new Date(currentDateTime.getFullYear(), currentDateTime.getMonth(), currentDateTime.getDate());
          // 檢查問卷開始時間必須在今天之後
          if (startDateTime < todayStart) {
              alert('開始時間必須在今天之後！');
              return;
          }
          // 檢查問卷開始時間在問卷結束時間之前
          if (startDateTime >= new Date(this.endTime)) {
              alert('開始時間必須在結束時間之前！');
              return;
          }
          // 檢查問卷結束時間是否有值且在問卷開始時間之後
          if (!this.endTime) {
              alert('請選擇問卷結束時間！');
              return;
          }
          const endDateTime = new Date(this.endTime);
          if (endDateTime <= startDateTime) {
              alert('問卷結束時間必須在問卷開始時間之後！');
              return;
          }
              // 檢查是否至少有一個問題
          if (this.questArr.length === 0) {
              alert('請新增至少一個問題！');
              return;
          }
          // 檢查每個問題是否有題目和至少一個選項有值
          const hasEmptyQuestion = this.questArr.some(quest => {
              // 確保 quest 不是 undefined
              if (!quest || !quest.question) {
                  alert('每個問題都必須有值！');
                  return true;  // 中斷迴圈，顯示警告
              }
              // 檢查題目本身是否為空
              if (!quest.question.trim()) {
                  alert('每個問題都必須有題目！');
                  return true;  // 中斷迴圈，顯示警告
              }
              // 檢查是否至少有一個選項有值
              const hasValueInOptions = quest.options.some(option => option.text && option.text.trim());
              if (!hasValueInOptions) {
                  alert('每個問題都必須至少有一個選項有值！');
                  return true;  // 中斷迴圈，顯示警告
              }
              return false;  // 問題有題目且至少有一個選項有值
          });
          if (hasEmptyQuestion) {
              // 顯示警告，不執行後續的儲存操作
              return;
          }





          const questionList = this.questArr.map((quest, quid) => {
              return {
                  quid: quid + 1,  // 如果 quid 從 1 開始，可以直接使用 quid
                  qnId:this.editingQuizId,
                  qTitle: quest.question,
                  optionType: quest.questionType,
                  necessary: false,  // 您可能需要另外處理
                  option: quest.options.map(option => option.text).join(','),
              };
          });
          const newQuestionnaire = {
              questionnaire: {
                  id:this.editingQuizId ,
                  title: this.questionName,
                  description: this.description,
                  published: false,
                  startDate: this.startTime,
                  endDate: this.endTime,  // 您可能需要另外處理
              },
              questionList: questionList,
          };
          // 將對象轉換為 JSON 字符串
          const requestBody = JSON.stringify(newQuestionnaire);
          // 發送 POST 請求
          fetch('http://localhost:8081/api/quiz/update', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify(newQuestionnaire),
          })
              .then(response => {
                  if (!response.ok) {
                      throw new Error(`HTTP error! Status: ${response.status}`);
                  }
                  return response.json();
              })
              .then(data => {
                  console.log(data);
              })
              .catch(error => console.error('Error:', error));
          alert('已經成功儲存問卷');
      },
      postToDB(questionIndex) {
                      // 檢查問卷名稱是否有值
                      if (!this.questionName || !this.questionName.trim()) {
              alert('請輸入問卷名稱！');
              return;
          }
          // 檢查描述內容是否有值
          if (!this.description || !this.description.trim()) {
              alert('請輸入描述內容！');
              return;
          }
          // 檢查問卷開始時間是否有值且在現在之後
          if (!this.startTime) {
              alert('請選擇問卷開始時間！');
              return;
          }
          const currentDateTime = new Date();
          const startDateTime = new Date(this.startTime);
          // 將現在的時間設置為今天的 00:00:00
          const todayStart = new Date(currentDateTime.getFullYear(), currentDateTime.getMonth(), currentDateTime.getDate());
          // 檢查問卷開始時間在今天之後並在問卷結束時間之前
          if (startDateTime < todayStart || startDateTime >= new Date(this.endTime)) {
              alert('開始時間必須在結束時間之前！');
              return;
          }
          // 檢查問卷結束時間是否有值且在問卷開始時間之後
          if (!this.endTime) {
              alert('請選擇問卷結束時間！');
              return;
          }
          const endDateTime = new Date(this.endTime);
          if (endDateTime <= startDateTime) {
              alert('問卷結束時間必須在問卷開始時間之後！');
              return;
          }
              // 檢查是否至少有一個問題
          if (this.questArr.length === 0) {
              alert('請新增至少一個問題！');
              return;
          }
          // 檢查每個問題是否有題目和至少一個選項有值
          const hasEmptyQuestion = this.questArr.some(quest => {
              // 確保 quest 不是 undefined
              if (!quest || !quest.question) {
                  alert('每個問題都必須有值！');
                  return true;  // 中斷迴圈，顯示警告
              }
              // 檢查題目本身是否為空
              if (!quest.question.trim()) {
                  alert('每個問題都必須有題目！');
                  return true;  // 中斷迴圈，顯示警告
              }
              // 檢查是否至少有一個選項有值
              const hasValueInOptions = quest.options.some(option => option.text && option.text.trim());
              if (!hasValueInOptions) {
                  alert('每個問題都必須至少有一個選項有值！');
                  return true;  // 中斷迴圈，顯示警告
              }
              return false;  // 問題有題目且至少有一個選項有值
          });
          if (hasEmptyQuestion) {
              // 顯示警告，不執行後續的儲存操作
              return;
          }




          const questionList = this.questArr.map((quest, quid) => {
              return {
                  quid: quid + 1,  // 如果 quid 從 1 開始，可以直接使用 quid
                  qnId:this.editingQuizId,
                  qTitle: quest.question,
                  optionType: quest.questionType,
                  necessary: false,  // 您可能需要另外處理
                  option: quest.options.map(option => option.text).join(','),
              };
          });
          const newQuestionnaire = {
              questionnaire: {
                  id:this.editingQuizId ,
                  title: this.questionName,
                  description: this.description,
                  published: true,
                  startDate: this.startTime,
                  endDate: this.endTime,  // 您可能需要另外處理
              },
              questionList: questionList,
          };
          // 將對象轉換為 JSON 字符串
          const requestBody = JSON.stringify(newQuestionnaire);
          // 發送 POST 請求
          fetch('http://localhost:8081/api/quiz/update', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify(newQuestionnaire),
          })
              .then(response => {
                  if (!response.ok) {
                      throw new Error(`HTTP error! Status: ${response.status}`);
                  }
                  return response.json();
              })
              .then(data => {
                  console.log(data);
              })
              .catch(error => console.error('Error:', error));
          alert('已經成功發布問卷');
          this.$router.push('/backquestHome');
      }
  }
}
</script>

<template>
  <div class="createQuestPageBody">
      <div class="createQuestHeader">
          <div>
              <h1>編輯問卷</h1>
              <label for="">問卷名稱</label>
              <input style="width: 90%;" type="text" name="" id="" v-model="questionName">
          </div>
          <div>
              <label for="">描述內容</label>
              <textarea style="width: 90%;height: 150px;" type="text" name="" id="" v-model="this.description"></textarea>
          </div>
          <div>
              <label for="">問卷開始時間</label>
              <input type="date" name="" id="" v-model="this.startTime">
              <label for="">問卷結束時間</label>
              <input type="date" name="" id="" v-model="this.endTime">
          </div>
          <div>
              <button v-on:click="createNewQuest()">新增問題</button>
              <!-- <button v-on:click="saveNewQuestPages()">儲存本次問卷(舊 local)</button> -->
              <button v-on:click="saveToDB()">儲存本次問卷</button>
              <button v-on:click="postToDB()">發布本次問卷</button>
          </div>
      </div>

      <div class="createQuest" v-for="(quest, questionIndex) in questArr" :key="questionIndex">
          <!-- 第一個Vfor做出每一個題目 -->
          <label>第{{ questionIndex + 1 }}題</label>
          <select v-model="quest.questionType" @change="handleQuestionTypeChange(questionIndex)">
              <option v-for="(type, index) in questionTypes" :key="index" :value="type">{{ type === 'radio' ? '單選' : type
                  === 'checkbox' ? '複選' : '簡答' }}</option>
          </select>
          <input type="text" v-model="quest.question" placeholder="輸入題目">
          <button @click="createNewOptions(questionIndex)">新增選項</button>

          <!-- selected是在input標籤在選擇radio或checkbox時，才會新增selected屬性在該選項上面 
          單選為on 複選為true-->
          <div class="NewOptions" v-for="(option, optionIndex) in quest.options" :key="optionIndex">
              <input v-if="quest.questionType === 'radio'" type="radio" :name="'radioGroup_' + questionIndex"
                  v-model="quest.options[optionIndex].selected">
              <input v-if="quest.questionType === 'checkbox'" type="checkbox"
                  v-model="quest.options[optionIndex].selected">
              <input type="text" placeholder="輸入選項" v-model="quest.options[optionIndex].text">
              <button style="background-color: red;" @click="deleteNewOptions(questionIndex, optionIndex)">刪除選項</button>
          </div>

          <button style="margin-left: 43px; background-color: red;"
              v-on:click="deleteNewQuest(questionIndex)">刪除問題</button>
      </div>
  </div>
</template>



<style lang="scss" scoped>
.createQuestPageBody {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #f0f0f0;
  padding: 20px;

  .createQuestHeader {
      width: 900px;
      height: auto;
      border: 1px solid #ccc;
      border-radius: 10px;
      padding: 20px;
      margin: 20px 0;
      background-color: #fff;
      display: flex;
      flex-direction: column;
      align-items: center;

      div {
          width: 100%;
          margin: 10px 0;
      }

      label {
          font-weight: bold;
          margin-right: 5px;
      }

      input[type="text"],
      input[type="date"] {
          width: 30%;
          padding: 10px;
          border: 1px solid #ccc;
          border-radius: 5px;
          margin-bottom: 10px;
          margin-right: 5px;
      }

      input[type="date"] {
          height: auto;
      }

      button {
          background-color: #007BFF;
          color: #fff;
          padding: 10px 20px;
          border: none;
          border-radius: 5px;
          cursor: pointer;
          margin-right: 10px;
      }

      button+button {
          margin-left: 10px;
      }
  }

  .createQuest {
      margin-top: 15px;
      width: 900px;
      border: 1px solid #ccc;
      border-radius: 10px;
      padding: 20px;
      background-color: #fff;

      label {
          font-weight: bold;
      }

      input[type="text"] {
          margin-top: 15px;
          margin-bottom: 15px;
          width: 80%;
          padding: 10px;
          border: 1px solid #ccc;
          border-radius: 5px;
          margin-right: 15px;
      }

      input[type="radio"],
      input[type="checkbox"] {
          width: 15px; /* 設置寬度為 20 像素 */
          height: 15px; /* 設置高度為 20 像素 */
          margin-right: 2%; /* 設置右邊外邊距為 10 像素 */
          margin-top: 1%;
      }

      button {
          background-color: #007BFF;
          color: #fff;
          padding: 10px 20px;
          border: none;
          border-radius: 5px;
          cursor: pointer;
      }
  }
}
</style>
