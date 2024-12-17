import { useState, useEffect } from 'react'
import axios from 'axios'
import ListItem from './ListItem'
import { addTodo } from './addList.js'

export default function List({ name, status, color }) {
  const [list, setList] = useState([]);
  const [text, setText] = useState("");
  useEffect(() => {
    axios({ method: "get", url: "http://localhost:8080/list", params: { "status": status }, })
      .then((response) => {
        setList(response.data);
        // console.log("데이터 가져왔다")
      })
      .catch((error) => {
        setList(null);
        console.log(error + "데이터 못 가져왔다")
      })
  }, [])
  function onContents(event) {
    setText(event.target.value);
  }

  return (
    <div style={{ display: 'inline-table', width: '300px', borderLeft: '1mm solid', borderColor: { color }, margin: '10px', padding: '10px' }}>
      <h3>{name}</h3>
      <hr />
      {list.map((item) => <ListItem key={item.id} data={item} />)}

      <form>
        <input type="text" placeholder={`${name}을 입력하세요.`} onChange={onContents} />
        <input type="submit" value="입력" onClick={() => { addTodo(text, status) }}></input>
      </form>
    </div>
  )
}