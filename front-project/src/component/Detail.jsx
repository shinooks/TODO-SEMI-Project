import React from 'react'
import { useEffect } from 'react-router-dom'
import axios from 'axios'
export default function Detail() {
  useEffect(() => {
    axios({ method: "get", })
  }, [])
  return (
    <div>detail</div>
  )
}
