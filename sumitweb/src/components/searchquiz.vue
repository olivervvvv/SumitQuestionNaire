<script setup>
    import { ref } from 'vue';
    import axios from 'axios';
    
    const quizData = ref({
      questionnaire: {
        title: '1',
        description: '333',
        published: false,
        startDate: '2023-11-20',
        endDate: '2023-11-30',
      },
      question_list: [
        {
          quId: '15',
          qTitle: '22',
          optionsType: '33',
          necessary: false,
          options: '><',
        },
        // Add more questions as needed
      ],
    });
    
    const createQuiz = () => {
      // Send POST request to the backend
      axios.post('http://localhost:8081/quiz/create', quizData.value)
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
        <div id="app">
            <form @submit.prevent="createQuiz">
              <!-- questionnaire -->
              <div>
                <label for="title">Quiz Title:</label>
                <input type="text" v-model="quizData.questionnaire.title" required>
              </div>
        
              <div>
                <label for="description">Quiz Description:</label>
                <input type="text" v-model="quizData.questionnaire.description" required>
              </div>
        
              <!-- question_list -->
              <div v-for="(question, index) in quizData.question_list" :key="index">
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
              </div>
        
              <button type="submit">Submit Form</button>
            </form>
            <button @click="getDataFromBackend">Get Data from Backend</button>
          </div>
          <table>
            <tr>
                <th>項目</th>
                <th>標題</th>
                <th>作者</th>
                <th>開始時間</th>
                <th>結束時間</th>
            </tr>
            <tr>
                <td>旅遊</td>
                <td>幾月去迪士尼好玩</td>
                <td>唐老鴨</td>
                <td>2023-11-20</td>
                <td>2023-11-30</td>
            </tr>
            

        </table>
    </template>
    
    <style>
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
    </style>