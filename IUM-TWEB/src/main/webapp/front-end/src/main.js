import {createApp} from 'vue'
import {reactive} from 'vue'
import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'

const app = createApp(App).use(router)
const bus = app.config.globalProperties.$bus = createEventBus()
app.mount('#app')

function createEventBus() {
    const listeners = {}
    function on(event, callback) {
        if (!listeners[event]) {
            listeners[event] = []
        }
        listeners[event].push(callback)
    }
    function off(event, callback) {
        if (listeners[event]) {
            listeners[event] = listeners[event].filter((listener) => listener !== callback)
        }
    }
    function emit(event, ...args) {
        if (listeners[event]) {
            listeners[event].forEach((listener) => {
                listener(...args)
            })
        }
    }
    return {
        on,
        off,
        emit
    }
}




export const store = reactive({
    data: {
        token: '',
        username: ''
    }
})
