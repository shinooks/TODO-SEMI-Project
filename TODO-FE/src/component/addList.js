import axios from 'axios'

export function addTodo(text, status) {
  axios({ method: "post", url: "http://localhost:8080/add", data: { content: text, status: status } })
    .then((response) => { console.log(response.data) })
    .catch(() => { })
    .finally(() => { console.log("보냈음") });
}


