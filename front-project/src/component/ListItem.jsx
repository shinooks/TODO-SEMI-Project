import React, { useState } from 'react'
import { Link } from 'react-router-dom'
export default function ListItem({ data }) {
  // console.log("데이터 출력한다", data.id, data.content, data.status);
  const [isChecked, setIsChecked] = useState(false);

  const handleCheckboxChange = () => {
    setIsChecked(!isChecked);
  };
  return (
    <div>
      <input
        type="checkbox"
        checked={isChecked}
        onChange={handleCheckboxChange}
      />
      {isChecked ? (
        <strike>{data.content}</strike> // 체크된 상태에서는 content에 strike 효과
      ) : (
        <Link to={`/detail/${data.id}`}>{data.content}</Link> // 체크 해제 상태에서는 detail 페이지로 이동하는 링크 생성
      )}
    </div>
  )
}
