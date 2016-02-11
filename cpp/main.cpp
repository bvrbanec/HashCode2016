#include <iostream>
#include <vector>

using namespace std;

int main() {
    int rows, cols, numOfDrones, deadline, maxLoad;
    cin >> rows >> cols >> numOfDrones >> deadline >> maxLoad;

    int numOfProdTypes;
    cin >> numOfProdTypes;
    vector<int> prodWeights(numOfProdTypes);
    for(int i = 0; i < numOfProdTypes; ++i){
        int weight;
        cin >> weight;
        prodWeights[i] = weight;
    }

    int numOfWarehouses;
    cin >> numOfWarehouses;
    vector<pair<int, int>> warehouseCoords(numOfWarehouses);
    vector<vector<int>> warehouseContent(numOfWarehouses);
    for(int i = 0; i < numOfWarehouses; ++i) {
        int r, c;
        cin >> r >> c;
        warehouseCoords[i] = make_pair(r, c);
        vector<int> content(numOfProdTypes);
        for(int j = 0; j < numOfProdTypes; ++j){
            int count;
            cin >> count;
            content[j] = count;
        }
        warehouseContent[i] = content;
    }

    int numOfOrders;
    cin >> numOfOrders;
    vector<pair<int, int>> ordersCoord(numOfOrders);
    vector<int> ordersItemsCount(numOfOrders);
    vector<vector<int>> ordersItemsTypes(numOfOrders);
    for(int i = 0; i < numOfOrders; ++i){
        int r, c;
        cin >> r >> c;
        ordersCoord[i] = make_pair(r, c);
        int numOfItems;
        cin >> numOfItems;
        ordersItemsCount[i] = numOfItems;
        vector<int> itemsTypes(numOfItems);
        for(int j = 0; j < numOfItems; ++j){
            int type;
            cin >> type;
            itemsTypes[j] = type;
        }
        ordersItemsTypes[i] = itemsTypes;
    }
*/
    /////////////////////////////////////////////
    cout << rows << cols << numOfDrones << deadline << maxLoad << endl;

/*    cout << numOfProdTypes << endl;

    for(int i = 0; i < numOfProdTypes; ++i){
        cout << prodWeights[i] << endl;
    }
    cout << numOfWarehouses << endl;
    for(int i = 0; i < numOfWarehouses; ++i) {
        cout << warehouseCoords[i].first << warehouseCoords[i].second << endl;
        for(int j = 0; j < numOfProdTypes; ++j){
            cout << warehouseContent[i][j] << " ";
        }
        cout << endl;
    }
    cout << numOfOrders << endl;
    for(int i = 0; i < numOfOrders; ++i) {
        cout << ordersCoord[i].first << ordersCoord[i].second << endl;
        cout << ordersItemsCount[i] << endl;
        for (int j = 0; j < ordersItemsCount[i]; ++j) {
            cout << ordersItemsTypes[i][j] << " ";
        }
        cout << endl;
    }
*/
    return 0;
}