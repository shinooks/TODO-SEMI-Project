import App from "../App"
import List from '../component/List'
import Detail from '../component/Detail'
const router =
{
    path: '/',
    element: <App />,
    children: [
        {
            path: "/",
            element: [
                <List key="1" name="해야할 일" status="0" color="Yellow" />,
                <List key="2" name="진행 중" status="1" color="Red" />,
                <List key="3" name="완료" status="2" color="Blue" />
            ]
        },
        {
            path: "/detail/:id",
            element: <Detail />
        }
    ]
}

export default router;