import React from 'react';
import { Link, Outlet } from 'react-router-dom';

export default function Root() {
  return (
    <div>
        <Link to='/'>Home</Link>
        <Link to='/join'>회원가입</Link>
        <Outlet />
    </div>
  );
}

