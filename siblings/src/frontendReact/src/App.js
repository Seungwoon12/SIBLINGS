import './App.css';
import axios from 'axios';
import { useState } from 'react';

function App() {
  const [text, setText] = useState('');
  
  axios.get('http://localhost:8888/test').then((res) => res.json()).then((data)=>{setText(data)}).catch((e) => {console.log(e)});

  return (
    <div className="App">
      {text}
    </div>
  );
}

export default App;
