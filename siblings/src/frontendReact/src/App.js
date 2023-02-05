import './App.css';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './pages/Home';
import Root from './pages/Root';
import Join from './pages/Join';

function App() {

  const router = createBrowserRouter([
    {
      path : '/',
      element : <Root />,
      children : [
        {
          index: true,
          element : <Home />
        },
        {
          path : '/join',
          element : <Join />
        }
      ]
    }
  ]);
  
  return (
    <RouterProvider router={router} />
  );
}

export default App;
