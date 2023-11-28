<template>
    <div class="questionnaire">
      <h2>問卷調查</h2>
  
      <form @submit.prevent="submitForm">
        <div class="form-des">
        <div class="form-group">
          <label for="title">標題：</label>
          <input type="text" v-model="formData.title" id="title" name="title" required>
        </div>
  
        <div class="form-group">
          <label for="startDate">開始日期：</label>
          <input type="date" v-model="formData.startDate" id="startDate" name="startDate" required>
        </div>
  
        <div class="form-group">
          <label for="endDate">結束日期：</label>
          <input type="date" v-model="formData.endDate" id="endDate" name="endDate" required>
        </div>
  
        <div class="form-group">
            <button @click="getDataFromBackend">搜尋趣</button>
        </div>
      </div>
      </form>

    </div>
    <div class="table-content">

      <table>
        <tr>
            <th>流水號</th>
            <th>標題</th>
            <th>作者</th>
            <th>開始時間</th>
            <th>結束時間</th>
        </tr>
        <!-- <tr>
            <td>旅遊</td>
            <td>幾月去迪士尼好玩</td>
            <td>唐老鴨</td>
            <td>2023-11-20</td>
            <td>2023-11-30</td>
        </tr> -->
        <tr v-for="(quiz, index) in quizData" :key="index">
          <td>{{ index+1 }}</td>
          <td>{{ quiz.title }}</td>
          <td>{{ quiz.description }}</td>
          <!-- <td>{{ quiz.questionnaire.published ? '已發佈' : '未發佈' }}</td> -->
          <td>{{ quiz.startDate }}</td>
          <td>{{ quiz.endDate }}</td>
      </tr>
        
  
    </table>
    </div>
  </template>


  <style>
      .questionnaire {
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
    width: 85vw;
    text-align: center;
    margin: auto;
    margin-top: 50px;
}

.questionnaire h2 {
    color: #333;
}
.form-des{
  display: flex;
  flex-direction: row;
}
.form-group {
    margin-bottom: 20px;
    padding-left: 2%;
    width: calc(100% - 70%);
}

.form-group label {
    display: block;
    font-size: 14px;
    color: #555;
    margin-bottom: 8px;
}

.form-group input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
    /* width: calc(100% - 70%); */
  }
  
  /* .form-group input[type="date"] {
      width: calc(100% - 70%);
    } */
    
    .form-group button {
        background-color: #4caf50;
        color: #fff;
        cursor: pointer;
        font-size: 16px;
        padding: 10px;
        border: none;
        border-radius: 4px;
    }
    
    .form-group button:hover {
        background-color: #45a049;
    }
    .table-content{
      display: flex;
      justify-content: center;
      margin-top: 3%;
    }
    table {
            border-collapse: collapse;
            margin-bottom: 20px;
            width: calc(100% - 10%);
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }
    </style>
  <script>
    import axios from 'axios';
    
    export default {
        data() {
            return {
                formData: {
                    title: '',
                    startDate: '',
                    endDate: '',
                },
                quizData: [],
            };
        },
        methods: {
            // submitForm() {
            //     console.log('表單提交:', this.formData);
            // },

            async getDataFromBackend() {
                try {
                  // this.preprocessDates();

                    const response = await axios.get('http://localhost:8081/quiz/searchQuestionnaireListEazy', {
                        params: {
                            title: this.formData.title,
                            startDate: this.formData.startDate,
                            endDate: this.formData.endDate,
                        
                        },
                    });
                    
                    console.log("startDate: "+this.formData.startDate);
                    console.log('Data from backend:', response.data.questionnaire);
                    // this.quizData = response.data.quizVoList;
                    // Update quizData with the data received from the backend
                    // Object.assign(this.formData, response.data);
                    // // 檢查 response.data 是否存在
                if (response.data.questionnaire) {
                  // 將後端回傳的資料存儲在 quizData 中
                  this.quizData = response.data.questionnaire;
                } else {
                  console.error('Error: Missing quizVoList property in response:', response.data);
                }
                } catch (error) {
                    console.error('Error getting data from backend:', error);
                    if (error.response) {
                console.error('Detailed error response:', error.response.data);
            }
                }
            },
        },
    };
    </script>