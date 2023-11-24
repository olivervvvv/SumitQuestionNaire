<script setup>
    import { ref } from 'vue';
    import axios from 'axios';
    import {RouterLink, RouterView} from 'vue-router'

    </script>
    
    <template>
        <div>
            <!-- 子路由中的連結 -->
            <router-link to="/searchtest">Go to Search Quiz</router-link>
            
            <!-- 使用 <router-link> 設置路由連結 -->
            <router-link to="/test">Go to Test Page</router-link>
                
            <router-link to="/bulidtest">Go to Test Page</router-link>
            
            <!-- 路由視圖區域，用於渲染當前路由的內容 -->
            <router-view></router-view>
          </div>
          
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
    