import { Outlet } from 'react-router-dom'
import axios from 'axios'
function App() {

  async function addTodo(event) {
    if (event.code == "Enter") {
      const value = event.currentTarget.content.value
      await axios({ method: 'post', url: 'http://localhost:8080/add', data: { "content": value, "status": 1 } })
        .then((response) => { console.log("데이터보냄", response.data) })
        .catch((error) => { console.log(error) })
    }
  }
  return (
    <>
      <h1>TO-DO 리스트</h1>
      <hr />
      <Outlet />
    </>
  )
}

export default App
