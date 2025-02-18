import {BrowserRouter} from 'react-router-dom';
import Footer from "./component/Footer";
import Header from "./component/Header";
import SideBar from "./component/SideBar";
import "./css/style.css";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <SideBar>
      </SideBar>
      <Footer />
    </BrowserRouter>
  );
}
export default App;
