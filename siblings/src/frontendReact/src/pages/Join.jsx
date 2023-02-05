import React, { useState } from 'react';

export default function Join() {
  const [info, setInfo] = useState({memberId : '', memberPw : ''});
  const handleChangeId = (e) => {
    setInfo( (inf)=>({...inf, memberId:e.target.value}));
  };
  const handleChangePw = (e) => {
    setInfo( (inf)=>({...inf, memberPw:e.target.value}));
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    if(info.memberId.trim().length === 0 || info.memberPw.trim().length === 0){
      alert('모든 정보를 입력해주세요.');
      return;
    }
    e.target.submit();
  };
  return (
    <div>
        <p>회원가입</p>
        <form method='post' action='http://localhost:8888/member/join' onSubmit={handleSubmit}>
          <input onChange={handleChangeId} id='memberId' name="memberId" type='text' placeholder='아이디'></input>
          <input onChange={handleChangePw} id='memberPw' name="memberPw" type='text' placeholder='비밀번호'></input>
          <button>가입하기</button>
        </form>
    </div>
  );
}

